package za.co.entelect.superman.superman.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Component
@Entity
@Table (name ="Transactions")
@AttributeOverride(name = "id" ,column = @Column(name = "TransactionID") )
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentID")
    private Payment payment;
    @Column(name = "Amount",nullable = false,unique = true)
    private BigDecimal price;
    private String method;
    private String transactionReference;
    private String status;
    @Column(name = "FailureCode",nullable = false,unique = true)
    private byte errorCode;
    private String failureMessage;
    @ManyToMany()
    @JoinTable(name = "VoucherHistory",
            joinColumns = @JoinColumn(name = "TransactionID"),
            inverseJoinColumns =  @JoinColumn(name = "VoucherID"))
    private List<Voucher> vouchersList;
}
