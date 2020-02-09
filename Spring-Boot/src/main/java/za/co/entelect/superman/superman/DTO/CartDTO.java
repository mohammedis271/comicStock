package za.co.entelect.superman.superman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class CartDTO {
    private Integer customerID;
    private Integer StockReferenceID;
    private String Title;
    private byte Quantity;
    private byte SeriesNumber;

}
