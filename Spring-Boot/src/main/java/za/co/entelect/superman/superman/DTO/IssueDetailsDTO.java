package za.co.entelect.superman.superman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.entelect.superman.superman.domain.Issue;
import za.co.entelect.superman.superman.domain.Stock;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class IssueDetailsDTO {
    private Integer StockReferenceId;

    private String issueTitle;

    private String issuePublisher;

    private Byte issueSeriesNumber;

    private String issueDescription;

    private BigDecimal Price;

    private Byte availableQuantity;

    private String issueCoverImage;

    private String condition;

    public IssueDetailsDTO(Stock stock) {
        StockReferenceId = stock.getStockReferenceId();
        issueTitle = stock.getIssue().getTitle();
        issuePublisher = stock.getIssue().getPublisher();
        issueSeriesNumber = stock.getIssue().getSeriesNumber();
        issueDescription = stock.getIssue().getDescription();
        Price = stock.getPrice();
        availableQuantity = stock.getAvailableQuantity();
        issueCoverImage = stock.getIssue().getCoverImagePath();
        condition = stock.getCondition();
    }
}
