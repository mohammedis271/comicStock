package za.co.entelect.superman.superman.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.entelect.superman.superman.domain.Orders;

@Repository
public interface OrdersSpringRepository extends JpaRepository<Orders,String> {

    @Query("select o from Orders o where o.customer.id = ?1")
    Page<Orders> findAllByCustomerId(Integer customerId, Pageable pageable);

    @Query("select o from Orders o where o.customer.id = ?1 and o.orderId = ?2")
    Orders findByCustomerIdAndOrderId(Integer customerId, Integer orderId);

}
