package za.co.entelect.superman.superman.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Entity
@Table (name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PaymentID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID", columnDefinition = "int")
    private Orders ReferenceNumber;
    private String BillingAddressLine1;
    private String BillingAddressLine2;
    private String BillingSuburb;
    private String BillingCity;
    private String BillingPostal;
    @OneToMany(mappedBy = "payment")
    private List<Transaction> transactionList = new ArrayList<>();

    public void setAddress(Address address) {
        setBillingAddressLine1(address.getLineOne());
        setBillingAddressLine2(address.getLineTwo());
        setBillingSuburb(address.getSuburb());
        setBillingCity(address.getCity());
        setBillingPostal(address.getPostal());
    }
}
