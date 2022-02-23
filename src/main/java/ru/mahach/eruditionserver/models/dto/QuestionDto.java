package ru.mahach.eruditionserver.models.dto;

import java.util.Objects;

public class QuestionDto {
    private Long id;
    private String text;
    private ItemDto item;
    private String imagePath;

    public QuestionDto() {
    }

    QuestionDto(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.item = builder.item;
        this.imagePath = builder.imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ItemDto getItem() {
        return item;
    }

    public static class Builder {
        private Long id;
        private String text;
        private ItemDto item;
        private String imagePath;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setItem(ItemDto item) {
            this.item = item;
            return this;
        }

        public QuestionDto build() {
            return new QuestionDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDto that = (QuestionDto) o;
        return id.equals(that.id) && text.equals(that.text) && item.equals(that.item) && Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, item, imagePath);
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", item=" + item +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
