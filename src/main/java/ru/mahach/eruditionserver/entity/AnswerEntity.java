package ru.mahach.eruditionserver.entity;

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
            name = "question",
            nullable = false
    )
    private Long question;

    @Column(
            name = "is_true",
            nullable = false
    )
    private Boolean isTrue;

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

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerEntity answer = (AnswerEntity) o;
        return id.equals(answer.id) && text.equals(answer.text) && question.equals(answer.question) && isTrue.equals(answer.isTrue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, question, isTrue);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", question=" + question +
                ", is_true=" + isTrue +
                '}';
    }
}
