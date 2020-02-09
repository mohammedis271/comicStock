package za.co.entelect.superman.superman.domain;

//import com.sun.xml.internal.fastinfoset.util.CharArray;
import lombok.*;
import org.springframework.stereotype.Component;
//import za.co.entelect.bootcamp.superman.order.Cart;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Component
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Customers")
@AttributeOverride(name = "id" ,column = @Column(name = "CustomerID") )
public class Customer implements Serializable {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String surname;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cartCompoundKey.customer")
//    private List<Cart> cartItems;


 public Customer(String email, String password, String name, String surname) {
  this.email = email;
  this.password = password;
  this.name = name;
  this.surname = surname;
 }

}
