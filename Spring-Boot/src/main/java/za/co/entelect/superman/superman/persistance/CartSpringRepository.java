package za.co.entelect.superman.superman.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.entelect.superman.superman.domain.Cart;
import za.co.entelect.superman.superman.domain.Stock;

import java.util.List;

@Repository
public interface CartSpringRepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c where c.cartCompoundKey.customerId = ?1")
    List<Cart> findByCustomerId(Integer customerId);

}
