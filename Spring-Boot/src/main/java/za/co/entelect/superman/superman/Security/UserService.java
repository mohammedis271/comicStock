package za.co.entelect.superman.superman.Security;


import za.co.entelect.superman.superman.domain.Customer;

public interface UserService {
    void save (Customer customer);

    Customer findbyEmail(String username);
}
