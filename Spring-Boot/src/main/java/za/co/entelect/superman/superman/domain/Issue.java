package za.co.entelect.superman.superman.domain;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
@Component
@Entity
@Table (name = "Issues")
@AttributeOverride(name = "id" ,column = @Column(name = "IssueID") )
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Byte seriesNumber;
    private Date publicationDate;
    private String publisher;
    private String Description;
    @Column(name = "CoverImage",nullable = false,unique = true)
    private String coverImagePath;
}
