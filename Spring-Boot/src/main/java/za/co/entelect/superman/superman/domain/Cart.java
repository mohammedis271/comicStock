package za.co.entelect.superman.superman.domain;


import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Component
@Entity
public class Cart {
    @EmbeddedId
    private CartCompoundKey cartCompoundKey;
    private Date modificationDate;
    private Byte quantity;
}
