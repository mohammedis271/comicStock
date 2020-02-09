package za.co.entelect.superman.superman.Service.payment.Payment;


import za.co.entelect.superman.superman.Service.payment.PaymentProxy.CustomerPaymentDTO;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;

import java.math.BigDecimal;


public class PaymentObjectAdaptor extends CustomerPaymentDTO {
    public PaymentObjectAdaptor(Customer orderCustomer, Orders orders, BigDecimal amount) {
        super(orderCustomer.getId()+"", orders.getReferenceNumber(), amount);
    }




}