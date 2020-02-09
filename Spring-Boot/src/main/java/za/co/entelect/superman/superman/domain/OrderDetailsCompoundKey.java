package za.co.entelect.superman.superman.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Component
@Embeddable
public class OrderDetailsCompoundKey implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StockReferenceID")
    private Stock StockReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID",columnDefinition = "int")
    private Orders order;




}
