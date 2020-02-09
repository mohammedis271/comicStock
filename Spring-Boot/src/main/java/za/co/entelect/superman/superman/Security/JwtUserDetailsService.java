package za.co.entelect.superman.superman.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.persistance.CustomerSpringRepository;

import java.util.ArrayList;
@Component
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private CustomerSpringRepository customerSpringRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer user = customerSpringRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

}
