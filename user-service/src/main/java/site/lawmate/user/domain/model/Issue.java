package site.lawmate.user.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issues")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString(exclude = {"id"})
public class Issue extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String law;
    private String title;
    private String content;
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;


    public static Issue of(String law, String title, String content, String attachment) {
        Issue issue = new Issue();
        issue.law = law;
        issue.title = title;
        issue.content = content;
        issue.attachment = attachment;
        return issue;
    }
}
