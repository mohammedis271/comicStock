package za.co.entelect.superman.superman.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.entelect.superman.superman.domain.Stock;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockDTO {

    private Integer StockReferenceId;

    private String issueTitle;

    private String Publisher;

    private Byte SeriesNumber;

    private String Description;

    private BigDecimal Price;

    private Byte AvailableQuantity;

    private String CoverImage;

    private String Condition;

    public StockDTO (Stock stock) {
        StockReferenceId = stock.getStockReferenceId();
        issueTitle = stock.getIssue().getTitle();
        Publisher = stock.getIssue().getPublisher();
        SeriesNumber = stock.getIssue().getSeriesNumber();
        Description = stock.getIssue().getDescription();
        Price = stock.getPrice();
        AvailableQuantity = stock.getAvailableQuantity();
        CoverImage = stock.getIssue().getCoverImagePath();
        Condition = stock.getCondition();
    }
}
