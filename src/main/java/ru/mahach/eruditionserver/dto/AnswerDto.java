package ru.mahach.eruditionserver.dto;

public class AnswerDto {
    private Long id;
    private String text;
    private Long questionId;
    private Boolean isTrue;

    AnswerDto(Long id, String text, Long questionId, Boolean isTrue) {
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

    public static class AnswerDtoBuilder {
        private Long id;
        private String text;
        private Long questionId;
        private Boolean isTrue;

        AnswerDtoBuilder(){
        }

        AnswerDtoBuilder setId(Long id){
            this.id = id;
            return this;
        }

        AnswerDtoBuilder setText(String text){
            this.text = text;
            return this;
        }

        AnswerDtoBuilder setQuestionId(Long questionId){
            this.questionId = questionId;
            return this;
        }

        AnswerDtoBuilder setTrue(Boolean isTrue){
            this.isTrue = isTrue;
            return this;
        }

        AnswerDto build(){
            return new AnswerDto(id, text, questionId, isTrue);
        }
    }
}
