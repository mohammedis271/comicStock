package za.co.entelect.superman.superman.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.persistance.CustomerSpringRepository;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CustomerSpringRepository customerSpringRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Customer customer){
        customer.setId(customer.getId());
        customer.setEmail(customer.getEmail());
        String password = customer.getPassword();
        customer.setPassword(bCryptPasswordEncoder.encode(password));
        customer.setName(customer.getName());
        customer.setSurname(customer.getSurname());
        customerSpringRepository.save(customer);
    }

    @Override
    public Customer findbyEmail(String username){
        return customerSpringRepository.findByEmail(username);
    }


}
