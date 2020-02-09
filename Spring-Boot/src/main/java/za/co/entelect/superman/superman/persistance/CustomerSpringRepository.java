package za.co.entelect.superman.superman.persistance;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import za.co.entelect.superman.superman.domain.Address;
import za.co.entelect.superman.superman.domain.Customer;
import za.co.entelect.superman.superman.domain.Orders;
import za.co.entelect.superman.superman.domain.Stock;

import java.util.List;

@Repository
public interface CustomerSpringRepository extends JpaRepository<Customer, Integer> {

    Customer getCustomerById (Integer customerId);


//    @Query("select a from Address a where a.customerId.id = ?1")
//    List<Address> findAddressesByCustomerId(Integer customerId);

    @Query("select o from Orders o where o.customer.id = ?1")
    Page<Orders> findOrdersbyCustomerId(Integer customerID, Pageable pageable);

    @Modifying
    @Query(value = "insert into Customers(CustomerID,Email,Password,Name,Surname) VALUES (:customerID,:email,:password,:name,:surname)",nativeQuery = true)
    @Transactional
    void addCustomer(@Param("customerID") Integer CustomerID, @Param("email") String Email, @Param("password") String Password,
                @Param("name") String Name, @Param("surname") String Surname);

    Boolean existsByEmail(String username);
    Customer findByEmail(String username);
//    Customer addCustomer (Integer Id,String Email, String password, String Name, String Surname );

//    Customer save(Customer customer);

}
