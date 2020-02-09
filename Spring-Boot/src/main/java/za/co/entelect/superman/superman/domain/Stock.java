package za.co.entelect.superman.superman.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockReferenceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IssueID")
    private Issue issue;
    private String condition;
    @Column(name = "AvailableQty",nullable = false,unique = true)
    private Byte availableQuantity;
    private BigDecimal price;
    private String comments;
}
