package za.co.entelect.superman.superman.Service.payment.Payment;

import za.co.entelect.superman.superman.Service.payment.PaymentProxy.CustomerPaymentDTO;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;

import java.math.BigDecimal;

public class ObjectAdapterFactory implements AbstractAdapterFactory{
    @Override
    public CustomerPaymentDTO getDTO(Customer orderCustomer, Orders orders, BigDecimal amount) {
        return new PaymentObjectAdaptor(orderCustomer, orders, amount);
    }
}