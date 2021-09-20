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
            sequenceName = "questions_id_seq",
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
            name = "item",
            nullable = false
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
        return id.equals(question.id) && text.equals(question.text) && item.equals(question.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, item);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", item=" + item +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
