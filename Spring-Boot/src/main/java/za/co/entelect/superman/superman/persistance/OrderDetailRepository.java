package za.co.entelect.superman.superman.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.entelect.superman.superman.domain.OrderDetail;
import za.co.entelect.superman.superman.domain.Orders;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {


}
