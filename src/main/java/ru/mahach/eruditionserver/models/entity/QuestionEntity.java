package ru.mahach.eruditionserver.models.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class QuestionEntity {
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
            name = "item_id",
            nullable = false
    )
    private Long itemId;

    @Column(
            name = "image_path"
    )
    private String imagePath;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<AnswerEntity> answers = new ArrayList<>();

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long item) {
        this.itemId = item;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity question = (QuestionEntity) o;
        return id.equals(question.id) && text.equals(question.text) && itemId.equals(question.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, itemId);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", item=" + itemId +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
