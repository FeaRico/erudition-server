package ru.mahach.eruditionserver.dto;

import java.util.List;

public class QuestionDto {
    private Long id;
    private String text;
    private Long itemId;
    private String imagePath;
    private List<AnswerDto> answers;

    public QuestionDto(Long id, String text, Long itemId, String imagePath, List<AnswerDto> answers) {
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
}
