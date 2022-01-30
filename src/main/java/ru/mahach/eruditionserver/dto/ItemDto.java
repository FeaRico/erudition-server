package ru.mahach.eruditionserver.dto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ItemDto {
    private Long id;
    private String name;
    private String imagePath;
    private List<QuestionDto> questions;

    ItemDto(Long id, String name, String imagePath, List<QuestionDto> questions) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public static class ItemDtoBuilder {
        private Long id;
        private String name;
        private String imagePath;
        private List<QuestionDto> questions;

        ItemDtoBuilder() {
        }

        public ItemDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ItemDtoBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemDtoBuilder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public ItemDtoBuilder setQuestions(List<QuestionDto> questions) {
            if (isNull(questions))
                questions = new ArrayList<>();

            this.questions = questions;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(id, name, imagePath, questions);
        }
    }
}
