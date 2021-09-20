package ru.mahach.eruditionserver.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "questions_id_seq"
    )
    @SequenceGenerator(
            name = "questions_id_seq",
            sequenceName = "questions_id_seq"
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
            name = "true_answer",
            nullable = false
    )
    private Long true_answer;

    @Column(
            name = "item"
    )
    private Long item;

    @Column(
            name = "image_path"
    )
    private String imagePath;

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

    public Long getTrue_answer() {
        return true_answer;
    }

    public void setTrue_answer(Long true_answer) {
        this.true_answer = true_answer;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id.equals(question.id) && text.equals(question.text) && true_answer.equals(question.true_answer) && item.equals(question.item) && Objects.equals(imagePath, question.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, true_answer, item, imagePath);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", true_answer=" + true_answer +
                ", item=" + item +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
