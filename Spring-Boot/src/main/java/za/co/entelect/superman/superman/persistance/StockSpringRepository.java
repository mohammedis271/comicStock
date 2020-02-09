package za.co.entelect.superman.superman.persistance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.entelect.superman.superman.domain.Stock;

@Repository
public interface StockSpringRepository extends JpaRepository<Stock, Integer> {

    Stock findByStockReferenceId(Integer id);

    Page<Stock> findAll(Pageable pageable);

    @Query("select s from Stock s where s.issue.title like %?1%")
    Page<Stock> findByIssueTitleContains(String title, Pageable pageable);

    @Query("select s from Stock s where s.issue.publisher like %?2%")
    Page<Stock> findByIssuePublisherContains(String publisher, Pageable pageable);

}
