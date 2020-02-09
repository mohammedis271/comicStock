package za.co.entelect.superman.superman.api;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.co.entelect.superman.superman.ErrorHandling.ValidationException;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.persistance.CustomerSpringRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class UserInfoController {

    private final CustomerSpringRepository customerSpringRepository;


    public UserInfoController(CustomerSpringRepository customerSpringRepository) {
        this.customerSpringRepository = customerSpringRepository;
    }


    @PostMapping("/user")
    public Boolean create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String username = body.get("email");
        if (customerSpringRepository.existsByEmail(username)){

            throw new ValidationException("Email already existed");

        }

        String password = body.get("password");
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        String name = body.get("name");
        String surname = body.get("surname");
        customerSpringRepository.save(new Customer(username, encodedPassword, name, surname));
        return true;
    }
}
