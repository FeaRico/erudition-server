package ru.mahach.eruditionserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;
import ru.mahach.eruditionserver.models.entity.ItemEntity;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Utility {
    public static AnswerDto createAnswerDto() {
        QuestionDto questionDto = createQuestionDto();
        return new AnswerDto(null, "testAnswer", true, questionDto);
    }

    public static QuestionDto createQuestionDto() {
        ItemDto item = createItemDto();
        return new QuestionDto.Builder()
                .setText("testQuestion")
                .setImagePath("test/path/quest")
                .setItem(item)
                .build();
    }

    public static ItemDto createItemDto() {
        return new ItemDto(null, "testItem", "test/path");
    }

    public static String objectToJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void equalsAnswers(AnswerDto expected, AnswerDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getIsTrue(), actual.getIsTrue());
        assertEquals(expected.getQuestion(), actual.getQuestion());
    }

    public static void equalsAnswers(AnswerDto expected, AnswerEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getIsTrue(), actual.getIsTrue());
    }

    public static void equalsQuestions(QuestionDto expected, QuestionDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getItem(), expected.getItem());
    }

    public static void equalsQuestions(QuestionDto expected, QuestionEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }

    public static void equalsItems(ItemDto expected, ItemDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }

    public static void equalsItems(ItemDto expected, ItemEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }
}
