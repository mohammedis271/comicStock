package za.co.entelect.superman.superman.domain;

//import com.sun.xml.internal.fastinfoset.util.CharArray;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Component
@Entity
@Table (name = "Addresses")
@AttributeOverride(name = "id" ,column = @Column(name = "AddressID") )
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    @Column(name = "AddressName")
    private String name;
    @Column(name = "AddressLine1")
    private String lineOne;
    @Column(name = "AddressLine2")
    private String lineTwo;
    @Column(name = "suburb")
    private String suburb;
    private String city;
    private String postal;
}
