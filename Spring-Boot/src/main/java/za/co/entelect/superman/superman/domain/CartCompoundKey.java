package za.co.entelect.superman.superman.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Component
@Embeddable
public class CartCompoundKey implements Serializable {

    @Column(name = "CustomerID")
    private int customerId;

    @ManyToOne
    @JoinColumn(name = "StockReferenceID")
    private Stock stockReference;
}
