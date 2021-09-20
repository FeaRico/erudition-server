package ru.mahach.eruditionserver.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "answers_id_seq"
    )
    @SequenceGenerator(
            name = "answers_id_seq",
            sequenceName = "answers_id_seq"
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
            name = "text"
    )
    private String text;

    @Column(
            name = "question"
    )
    private Long question;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id.equals(answer.id) && text.equals(answer.text) && question.equals(answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, question);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", question=" + question +
                '}';
    }
}
