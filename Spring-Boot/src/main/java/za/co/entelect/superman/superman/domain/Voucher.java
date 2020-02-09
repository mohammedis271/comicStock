package za.co.entelect.superman.superman.domain;

import lombok.*;
import za.co.entelect.superman.superman.domain.Transaction;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@AttributeOverride(name = "id" ,column = @Column(name = "VoucherID") )
public class Voucher {
    @Id
    private Integer id;
    private String status;
    private BigDecimal value;
    private Date purchaseDate;
    @ManyToMany()
            @JoinTable(name = "VoucherHistory",
                        joinColumns = @JoinColumn(name = "VoucherId", columnDefinition = "int"),
                        inverseJoinColumns =  @JoinColumn(name = "TransactionID"))
    List<Transaction> transactionList;
}
