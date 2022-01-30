package ru.mahach.eruditionserver.dto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class QuestionDto {
    private Long id;
    private String text;
    private Long itemId;
    private String imagePath;
    private List<AnswerDto> answers;

    QuestionDto(Long id, String text, Long itemId, String imagePath, List<AnswerDto> answers) {
        this.id = id;
        this.text = text;
        this.itemId = itemId;
        this.imagePath = imagePath;
        this.answers = answers;
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

    public static class QuestionDtoBuilder {
        private Long id;
        private String text;
        private Long itemId;
        private String imagePath;
        private List<AnswerDto> answers;

        QuestionDtoBuilder() {
        }

        QuestionDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        QuestionDtoBuilder setText(String text) {
            this.text = text;
            return this;
        }

        QuestionDtoBuilder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        QuestionDtoBuilder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        QuestionDtoBuilder setAnswers(List<AnswerDto> answers) {
            if (isNull(answers))
                answers = new ArrayList<>();

            this.answers = answers;
            return this;
        }

        QuestionDto build() {
            return new QuestionDto(id, text, itemId, imagePath, answers);
        }
    }
}
