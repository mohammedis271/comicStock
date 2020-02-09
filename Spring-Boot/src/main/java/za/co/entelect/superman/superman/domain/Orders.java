package za.co.entelect.superman.superman.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.persister.walking.internal.FetchStrategyHelper;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
@Entity
@Table (name = "Orders")

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private String referenceNumber;
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;
    private Date orderDate;
    private String status;
    private Date deliveryDate;
    private BigDecimal deliveryCost;
    private String trackingNum;
    private String addressLine1;
    private String addressLine2;
    private String suburb;
    private String city;
    private String postal;
    private String deliveryMethod;
    private String instructions;

    public void setAddress(Address address) {
        setAddressLine1(address.getLineOne());
        setAddressLine2(address.getLineTwo());
        setSuburb(address.getSuburb());
        setCity(address.getCity());
        setPostal(address.getPostal());
    }
}