package za.co.entelect.superman.superman.Service.payment.Payment;

import java.math.BigDecimal;

public interface addTransaction {
    void addTransaction(String orderReferenceId,BigDecimal amount);
}
