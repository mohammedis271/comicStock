package za.co.entelect.superman.superman.api;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.superman.superman.Service.cart.CartManagementService;
import za.co.entelect.superman.superman.Service.checkout.CheckOutCartService;
import za.co.entelect.superman.superman.domain.Address;
import za.co.entelect.superman.superman.domain.Cart;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;
import za.co.entelect.superman.superman.dto.AddressDTO;
import za.co.entelect.superman.superman.dto.CartDTO;
import za.co.entelect.superman.superman.dto.ResponseCartDTO;
import za.co.entelect.superman.superman.dto.StockDTO;
import za.co.entelect.superman.superman.exceptions.*;
import za.co.entelect.superman.superman.persistance.AddressSpringRepository;
import za.co.entelect.superman.superman.persistance.CustomerSpringRepository;

import java.util.List;

@RestController
@RequestMapping("/Cart")
public class CartController {

    private final Logger LOGGER   = LoggerFactory.getLogger(BrowsingController.class);

    private CartManagementService cartManagementService;
    private CustomerSpringRepository customerSpringRepository;
    private CheckOutCartService checkOutCart;
    private AddressSpringRepository addressSpringRepository;

    @Autowired
    public CartController(CartManagementService cartManagementService, CheckOutCartService checkOutCart, CustomerSpringRepository customerSpringRepository, AddressSpringRepository addressSpringRepository) {
        this.cartManagementService = cartManagementService;
        this.checkOutCart = checkOutCart;
        this.customerSpringRepository = customerSpringRepository;
        this.addressSpringRepository = addressSpringRepository;
    }

    @GetMapping("/{customerID}")
    public ResponseEntity<List<CartDTO>> getCart(@PathVariable Integer customerID) throws CartNotFoundException {

        if(customerID != null)
        {
            try
            {

                LOGGER.info("Trying to retrieve cart for customer with ID {}", customerID.toString());
                return ResponseEntity.ok(cartManagementService.getCustomerCart(customerID));

            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new CartNotFoundException("Cart was not found", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("CustomerId is null");
        }


    }
    @DeleteMapping("/Discard/customerID")
    public ResponseEntity<String> emptyCart(@PathVariable Integer customerID) throws CartNotFoundException {

        if(customerID != null)
        {
            try
            {
                LOGGER.info("Cart for {} is deleted", customerID.toString());
                cartManagementService.DeleteCart(customerID);
                return ResponseEntity.ok("Cart Deleted");
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new CartNotFoundException("Cart could not be deleted", e.getCause());

            }
        }
        else
        {
            throw new NullPointerException("CustomerId is null");
        }


    }

    @DeleteMapping("/deleteItem")
    public ResponseEntity<List<CartDTO>> deleteSingleItem(@RequestBody ResponseCartDTO cart) throws UnableToDeleteCartEntryException {

        if(cart != null)
        {
            try
            {
                LOGGER.info("Item {} deleted from cart", cart.toString());
                return ResponseEntity.ok(cartManagementService.removeCartItem(cart));
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new UnableToDeleteCartEntryException("Item was not added to cart", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("Cart is null");
        }


    }

    @PostMapping(
            path = "/additem",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<CartDTO>> addNewItem(@RequestBody ResponseCartDTO cart) throws UnableToCreateCartEntryException {

        if(cart != null)
        {
            try
            {
                LOGGER.info("Item {} added to cart", cart.toString());
                return  ResponseEntity.ok(cartManagementService.addCartItem(cart));
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new UnableToCreateCartEntryException("Item was not added to cart", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("Cart is null");
        }

    }

    @GetMapping("/checkout/{customerID}")
    @Transactional
    public ResponseEntity<Orders> checkout(@PathVariable  Integer customerID) throws Exception {

        Address userAddress = null;
        Customer customer = null;

        if(customerID != null)
        {
            try
            {
                userAddress = addressSpringRepository.findAllByCustomerId(customerID).get(1);
                customer = customerSpringRepository.getCustomerById(customerID);
                LOGGER.info("Order created for {} ", customerID.toString());
                return ResponseEntity.ok(checkOutCart.checkoutCart(userAddress, customer));

            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new UnableToCreateOrderException("Order was not created", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("CustomerId is null");
        }
    }

    @PutMapping("/updateItem")
    public boolean updateItem(@RequestBody Cart cart) throws UnableToUpdateItemException {
        if(cart != null)
        {
            try
            {
                cartManagementService.updateQuantity(cart);
                LOGGER.info("Updated item with new values {} ", cart.toString());
                return  true;
            }
            catch(DataIntegrityViolationException e)
            {
                LOGGER.error(e.getMessage());
                throw new UnableToUpdateItemException("Could not update item", e.getCause());
            }
        }
        else
        {
            throw new NullPointerException("Item trying to update is null");
        }

    }

}
