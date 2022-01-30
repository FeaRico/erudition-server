package ru.mahach.eruditionserver.dto;

import java.util.List;

public class ItemDto {
    private Long id;
    private String name;
    private String imagePath;
    private List<QuestionDto> questions;

    public ItemDto(Long id, String name, String imagePath, List<QuestionDto> questions) {
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
}
