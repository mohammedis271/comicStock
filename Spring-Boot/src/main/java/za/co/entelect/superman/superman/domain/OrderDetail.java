package za.co.entelect.superman.superman.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@Entity
@Table (name = "OrderDetails")
public class OrderDetail {
    @Id
    private OrderDetailsCompoundKey orderDetailsId;
    private BigDecimal Price;
    private int Quantity;



}
