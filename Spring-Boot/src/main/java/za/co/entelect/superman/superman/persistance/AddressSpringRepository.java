package za.co.entelect.superman.superman.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.entelect.superman.superman.domain.Address;
import za.co.entelect.superman.superman.domain.Cart;

import java.util.List;

import java.util.List;

@Repository
public interface AddressSpringRepository extends JpaRepository<Address, Integer> {

    @Query("Select a from Address a where a.customer.id = ?1")
    List<Address> findAllByCustomerId(Integer customerId);
}
