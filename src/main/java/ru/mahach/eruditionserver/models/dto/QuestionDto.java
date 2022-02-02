package ru.mahach.eruditionserver.models.dto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class QuestionDto {
    private Long id;
    private String text;
    private Long itemId;
    private String imagePath;
    private List<AnswerDto> answers;

    QuestionDto(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.itemId = builder.itemId;
        this.imagePath = builder.imagePath;
        this.answers = builder.answers;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public static class Builder {
        private Long id;
        private String text;
        private Long itemId;
        private String imagePath;
        private List<AnswerDto> answers;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder setAnswers(List<AnswerDto> answers) {
            if (isNull(answers))
                answers = new ArrayList<>();

            this.answers = answers;
            return this;
        }

        public QuestionDto build() {
            return new QuestionDto(this);
        }
    }
}
