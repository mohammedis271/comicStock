package za.co.entelect.superman.superman.Service.payment.Payment;

import za.co.entelect.superman.superman.Service.payment.PaymentProxy.CustomerPaymentDTO;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;

import java.math.BigDecimal;

public interface AbstractAdapterFactory {
    CustomerPaymentDTO getDTO(Customer orderCustomer, Orders orders, BigDecimal amount);
}
