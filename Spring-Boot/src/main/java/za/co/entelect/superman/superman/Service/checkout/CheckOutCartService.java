
package za.co.entelect.superman.superman.Service.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.entelect.superman.superman.Service.payment.Payment.PaymentObjectAdaptor;
import za.co.entelect.superman.superman.domain.*;
import za.co.entelect.superman.superman.persistance.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;

@Component
public class CheckOutCartService {
    private final String PENDING = "pending";
    private final String FAIL = "Failed";
    private final String SUCCESS = "Success";
    private final String ERROR_TWO = "Contact Bank";
    private final String VOUCHER = "Voucher";
    private final String VOUCHER_FAILURE = "Not enough voucher funds";
    private final String CREDIT_CARD = "Credit Card";
    private int quantityRestriction = 5;

    private Customer orderCustomer;
    private CustomerSpringRepository customerSpringRepository;
    private OrdersSpringRepository ordersSpringRepository;
    private OrderDetailRepository orderDetailRepository;
    private CartSpringRepository cartSpringRepository;
    private StockSpringRepository stockSpringRepository;
    private PaymentSpringRepository paymentSpringRepository;
    private TransactionSpringRepository transactionSpringRepository;
    private VoucherSpringRepository  voucherRepo;
    private Address deliveryAddress;

    @Autowired
    public CheckOutCartService(Customer orderCustomer,
                               Address deliveryAddress,
                               OrdersSpringRepository orderRepo,
                               OrderDetailRepository  orderDetailsRepo,
                               CartSpringRepository cartRepo,
                               StockSpringRepository stockRepo,
                               TransactionSpringRepository transactionRepo,
                               PaymentSpringRepository paymentSpringRepository,
                               VoucherSpringRepository voucherRepo,
                               CustomerSpringRepository customerSpringRepository) {
        this.orderCustomer = orderCustomer;
        this.deliveryAddress = deliveryAddress;
        this.ordersSpringRepository = orderRepo;
        this.orderDetailRepository = orderDetailsRepo;
        this.cartSpringRepository = cartRepo;
        this.stockSpringRepository = stockRepo;
        this.transactionSpringRepository = transactionRepo;
        this.customerSpringRepository = customerSpringRepository;
        this.paymentSpringRepository = paymentSpringRepository;
        this.voucherRepo = voucherRepo;
    }

    public Orders checkoutCart(Address billingAddress, Customer customer) throws Exception {
        Orders newOrder = new Orders();
        newOrder.setReferenceNumber(generateReferenceNumber());
        newOrder.setCustomer(customer);
        newOrder.setOrderDate(Date.from(Instant.now()));
        double totalPerProduct = 0.0;
        List<Cart> carts = cartSpringRepository.findByCustomerId(customer.getId());
        for (Cart cart: carts) {
            double cartQuantity = cart.getQuantity();
            double stockPrice = cart.getCartCompoundKey().getStockReference().getPrice().doubleValue();
            totalPerProduct+= cartQuantity*stockPrice;
        }

        BigDecimal total =BigDecimal.valueOf(totalPerProduct);
        newOrder.setDeliveryCost(total);
        newOrder.setAddressLine1(billingAddress.getLineOne());
        newOrder.setAddressLine2(billingAddress.getLineTwo());
        newOrder.setSuburb(billingAddress.getSuburb());
        newOrder.setCity(billingAddress.getCity());
        newOrder.setPostal(billingAddress.getPostal());
        newOrder.setStatus("pending");
        newOrder.setDeliveryMethod("Normal");
        newOrder.setInstructions("Deliver between 8am and 4pp");
        for (Cart x: getCustomerCart(customer.getId())) {
            cartSpringRepository.delete(x);
        }
        return ordersSpringRepository.save(newOrder);
    }

    private List<Cart> getCustomerCart(Integer customerID) {
        return cartSpringRepository.findByCustomerId(customerID);
    }

    private Payment generatePayment(Orders orders, Address address) throws Exception{
        Payment payment = new Payment();
        payment.setAddress(address);
        payment.setReferenceNumber(orders);
        payment = paymentSpringRepository.save(payment);
        return payment;
    }

    private Boolean processCardTransaction(Payment payment, Orders orders, BigDecimal amountToBePaid) throws Exception {
        boolean output = false;
        Transaction transaction = new Transaction();
        PaymentObjectAdaptor paymentDTO = new PaymentObjectAdaptor(orderCustomer, orders, amountToBePaid);
        transaction.setMethod(CREDIT_CARD);
        transaction.setPrice(amountToBePaid);
        transaction.setPayment(payment);
        transaction.setTransactionReference(generateTransactionReference());
        if(paymentDTO.makePayment(paymentDTO).compareTo(SUCCESS) == 0) {
            transaction.setErrorCode((byte)0);
            transaction.setStatus(SUCCESS);
            output = true;
        } else {
            transaction.setErrorCode((byte)2);
            transaction.setFailureMessage(ERROR_TWO);
            transaction.setStatus(FAIL);
        }
        transactionSpringRepository.save(transaction);
        return output;
    }

    private boolean processVoucherTransaction(Payment payment, Voucher voucher, BigDecimal amountToBePaid) throws Exception{
        boolean output = false;
        Transaction transaction =  new Transaction();
        transaction.setMethod(VOUCHER);
        transaction.setTransactionReference(generateTransactionReference());
        transaction.setPayment(payment);
        transaction.setPrice(amountToBePaid);
        if(amountToBePaid.compareTo(voucher.getValue()) > 0) {
            transaction.setErrorCode((byte) 7);
            transaction.setFailureMessage(VOUCHER_FAILURE);
            transaction.setStatus(FAIL);
            voucher.setStatus("Unused");
        } else {
            transaction.setErrorCode((byte)0);
            transaction.setStatus(SUCCESS);
            output = true;
            voucher.setValue(voucher.getValue().subtract(amountToBePaid));
            voucher.setStatus("used");
        }
        voucherRepo.save(voucher);
        transactionSpringRepository.save(transaction);
        return output;
    }

    private boolean validateOrderQuantities(List<Cart> cart) throws Exception {
        Map<String, List<Cart>> itemsWithProblems = new HashMap<>();
        itemsWithProblems.put("NotEnoughStock", itemsNotEnoughStock(cart));
        itemsWithProblems.put("AboveQuantityRestriction", itemsAboveRestriction(cart));
        if (itemsWithProblems.get("NotEnoughStock").size() <= 0 && itemsWithProblems.get("NotEnoughStock").size() <= 0 ) {
            return true;
        } else {
            return false;
        }
    }

    private List<Cart> itemsNotEnoughStock(List<Cart> cart) throws Exception {
        List<Cart> outOfStock = new ArrayList<>();
        for (Cart c : cart) {
            if (stockSpringRepository.getOne(c.getCartCompoundKey().getStockReference().getStockReferenceId()).getAvailableQuantity() < c.getQuantity()){
                outOfStock.add(c);
            }
        }
        return outOfStock;
    }

    private List<Cart> itemsAboveRestriction(List<Cart> cart) throws Exception{
        List<Cart> aboveRestriction = new ArrayList<>();
        for (Cart c : cart) {
            if (stockSpringRepository.getOne(c.getCartCompoundKey().getStockReference().getStockReferenceId()).getAvailableQuantity() > quantityRestriction){
                aboveRestriction.add(c);
            }
        }
        return aboveRestriction;
    }

    private String getTrackingNumber() {
        String random;
            int randomInt = (int)(Math.random()*((1-Integer.MAX_VALUE)))+1;
            random = String.valueOf(Math.abs(randomInt));
        return random;
    }

    private String generateReferenceNumber() throws Exception {
        String alphabet = "ABCDEFGHIJKLMNOPQRS";
        String random;
        String formatted;
            SecureRandom numbers = new SecureRandom();
            int num = numbers.nextInt(100000);
            formatted = String.format("%05d", num);
            for (int i = 0; i < 4; i++) {
                char tempRandom = alphabet.charAt(numbers.nextInt(alphabet.length()));
                formatted = (char)tempRandom + formatted;
            }
        random = formatted;
        return random;
    }

    private String generateTransactionReference() {
        String alphabet = "ABCDEFGHIJKLMNOPQRS";
        String random = "AAAAAAAAAAAAAAAA";
        String tempRandom = "";
        Random numbers = new Random();
            tempRandom = "";
            for (int i = 0; i < random.length(); i++) {
                tempRandom += (char) alphabet.charAt(numbers.nextInt(alphabet.length()));
            }
        random = tempRandom;
        return random;
    }

    private void populateOrderFromCart(List<Cart> cart, Orders order) throws Exception{
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Cart c:
             cart) {
            Stock stockItem = stockSpringRepository.getOne(c.getCartCompoundKey().getStockReference().getStockReferenceId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(stockItem.getPrice());
            orderDetail.setQuantity(c.getQuantity());
            OrderDetailsCompoundKey key = new OrderDetailsCompoundKey();
            key.setOrder(order);
            key.setStockReference(stockSpringRepository.getOne(c.getCartCompoundKey().getStockReference().getStockReferenceId()));
            orderDetail.setOrderDetailsId(key);
            orderDetails.add(orderDetail);
            stockItem.setAvailableQuantity((byte)(stockItem.getAvailableQuantity() - orderDetail.getQuantity()));
            stockSpringRepository.save(stockItem);
            orderDetailRepository.save(orderDetail);
        }
        cartSpringRepository.delete(cart.get(1));
    }

    private Orders createOrder(String deliveryMethod, String deliveryInstructions) throws Exception{
        Orders orders = new Orders();
        orders.setReferenceNumber(generateReferenceNumber());
        orders.setTrackingNum(getTrackingNumber());
        orders.setAddress(deliveryAddress);
        orders.setDeliveryMethod(deliveryMethod);
        orders.setInstructions(deliveryInstructions);
        orders.setOrderDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        orders.setDeliveryDate(calendar.getTime());
        orders.setCustomer(orderCustomer);
        orders.setStatus(PENDING);
        orders.setDeliveryCost(new BigDecimal("0"));
        return ordersSpringRepository.save(orders);
    }
}
