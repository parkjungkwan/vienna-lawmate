package site.lawmate.user.domain.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@ToString(exclude = {"id"})
public class Question extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String law;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User writer;


    @Builder(builderMethodName = "builder")
    public Question(Long id, String law, String title, String content, User writer) {
        this.id = id;
        this.law = law;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static Question of(String law, String title, String content, User writer) {
        Question question = new Question();
        question.law = law;
        question.title = title;
        question.content = content;
        question.writer = writer;
        return question;
    }
}
