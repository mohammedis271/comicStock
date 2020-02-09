package za.co.entelect.superman.superman.Service.payment.PaymentProxy;

import java.math.BigDecimal;
import java.util.Objects;

public class CustomerPaymentDTO implements IpaymentProxyService {
    private String customerRefNumber;
    private String paymentRefNumber;
    private BigDecimal amount;

    public CustomerPaymentDTO(String customerRefNumber, String paymentRefNumber, BigDecimal amount) {
        this.customerRefNumber = customerRefNumber;
        this.paymentRefNumber = paymentRefNumber;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerPaymentDTO that = (CustomerPaymentDTO) o;
        return Objects.equals(customerRefNumber, that.customerRefNumber) &&
                Objects.equals(paymentRefNumber, that.paymentRefNumber) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(customerRefNumber, paymentRefNumber, amount);
    }


    @Override
    public String makePayment(CustomerPaymentDTO customerPayment) {
        return "Success";
    }
}
