package ru.mahach.eruditionserver.models.dto;

import ru.mahach.eruditionserver.validation.Marker;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Objects;

public class AnswerDto {
    @Null(groups = Marker.Create.class)
    @NotNull(groups = Marker.Update.class)
    @Min(value = 1, groups = Marker.Update.class)
    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Boolean isTrue;

    private QuestionDto question;

    public AnswerDto() {
    }

    public AnswerDto(Long id, String text, Boolean isTrue, QuestionDto question) {
        this.id = id;
        this.text = text;
        this.isTrue = isTrue;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getIsTrue() {
        return isTrue;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDto answerDto = (AnswerDto) o;
        return id.equals(answerDto.id) && text.equals(answerDto.text) && isTrue.equals(answerDto.isTrue) && question.equals(answerDto.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, isTrue, question);
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isTrue=" + isTrue +
                ", question=" + question +
                '}';
    }
}
