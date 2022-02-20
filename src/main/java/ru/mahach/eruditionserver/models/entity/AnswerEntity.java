package ru.mahach.eruditionserver.models.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answers")
public class AnswerEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "answers_id_seq"
    )
    @SequenceGenerator(
            name = "answers_id_seq",
            sequenceName = "answers_id_seq",
            allocationSize = 1
    )
    @Column(
            name = "id",
            nullable = false,
            unique = true,
            insertable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "text",
            nullable = false
    )
    private String text;

    @Column(
            name = "is_true",
            nullable = false
    )
    private Boolean isTrue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isTrue() {
        return isTrue;
    }

    public void setTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerEntity that = (AnswerEntity) o;
        return id.equals(that.id) && text.equals(that.text) && isTrue.equals(that.isTrue) && question.equals(that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, isTrue, question);
    }

    @Override
    public String toString() {
        return "AnswerEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isTrue=" + isTrue +
                ", question=" + question +
                '}';
    }
}
