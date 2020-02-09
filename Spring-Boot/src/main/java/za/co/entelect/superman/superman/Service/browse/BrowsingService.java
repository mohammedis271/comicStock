package za.co.entelect.superman.superman.Service.browse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.entelect.superman.superman.domain.Stock;
import za.co.entelect.superman.superman.dto.StockDTO;
import za.co.entelect.superman.superman.persistance.IssueSpringRepository;
import za.co.entelect.superman.superman.persistance.StockSpringRepository;

import javax.sql.DataSource;

@Component
public class BrowsingService {

    private StockSpringRepository stockSpringRepository;
    private IssueSpringRepository issueSpringRepository;
    private DataSource dataSource;

    @Autowired
    public BrowsingService(StockSpringRepository stockSpringRepository) {
        this.stockSpringRepository = stockSpringRepository;
    }
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Page<Stock> getAllStock(Pageable pageable) {

        return stockSpringRepository.findAll(pageable);
    }


    public Page<Stock> GetStockbyPublisher(
            String publisher, Pageable pageable) {
        return stockSpringRepository.findByIssuePublisherContains(publisher, pageable);
    }


    public Page<Stock> GetStockbyTitle (String title, Pageable pageable)
    {
        return stockSpringRepository.findByIssueTitleContains(title, pageable);
    }

    public Stock getfindByStockReferenceId(Integer ID){
        return stockSpringRepository.findByStockReferenceId(ID);
    }
}


