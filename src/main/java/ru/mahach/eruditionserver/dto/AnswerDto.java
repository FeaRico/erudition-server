package ru.mahach.eruditionserver.dto;

public class AnswerDto {
    private Long id;
    private String text;
    private Long questionId;
    private Boolean isTrue;

    public AnswerDto(Long id, String text, Long questionId, Boolean isTrue) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
        this.isTrue = isTrue;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Boolean isTrue() {
        return isTrue;
    }
}
