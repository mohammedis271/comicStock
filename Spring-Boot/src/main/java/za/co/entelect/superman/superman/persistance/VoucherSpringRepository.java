package za.co.entelect.superman.superman.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.entelect.superman.superman.domain.Voucher;

@Repository
public interface VoucherSpringRepository extends JpaRepository<Voucher, Integer> {

}
