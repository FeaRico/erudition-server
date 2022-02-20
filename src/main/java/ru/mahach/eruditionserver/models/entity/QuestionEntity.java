package ru.mahach.eruditionserver.models.entity;

import javax.persistence.*;
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
            name = "image_path"
    )
    private String imagePath;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return id.equals(that.id) && text.equals(that.text) && Objects.equals(imagePath, that.imagePath) && item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, imagePath, item);
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", item=" + item +
                '}';
    }
}
