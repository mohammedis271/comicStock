package za.co.entelect.superman.superman.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCartDTO {

    private Integer customerID;
    private Integer stockReferenceID;
    private Integer quantity;

}
