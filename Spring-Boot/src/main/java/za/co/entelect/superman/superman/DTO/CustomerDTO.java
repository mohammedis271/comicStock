package za.co.entelect.superman.superman.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.entelect.superman.superman.domain.Customer;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Integer ID;
    private String name;
    private String surname;
    private String email;
    private String password;

    public CustomerDTO(Customer customer) {
        this.ID = customer.getId();
        this.name = customer.getName();
        this.surname = customer.getSurname();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
    }
}
