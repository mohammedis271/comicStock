package za.co.entelect.superman.superman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
public class AddressDTO {
    private String addressName;
    private String addressLine1;
    private String addressLine2;
    private String suburb;
    private String city;
    private String postal;


}
