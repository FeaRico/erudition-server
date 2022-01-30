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
            name = "question_id",
            nullable = false
    )
    private Long questionId;

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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long question) {
        this.questionId = question;
    }

    public Boolean isTrue() {
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
        return id.equals(answer.id) && text.equals(answer.text) && questionId.equals(answer.questionId) && isTrue.equals(answer.isTrue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, questionId, isTrue);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", question=" + questionId +
                ", is_true=" + isTrue +
                '}';
    }
}
