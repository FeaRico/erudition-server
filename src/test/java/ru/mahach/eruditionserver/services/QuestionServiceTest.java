package ru.mahach.eruditionserver.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;
import ru.mahach.eruditionserver.repository.QuestionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class QuestionServiceTest {
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @Autowired
    QuestionServiceTest(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @Test
    void create() {
        QuestionDto questionDto = createQuestionDto(11111);

        Optional<QuestionDto> questionDtoOptional = questionService.create(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        List<QuestionEntity> foundQuestions = questionRepository.findAll();
        assertEquals(1, foundQuestions.size());

        equalsQuestions(savedQuestion, foundQuestions.get(0));
    }

    @Test
    void update() {
        QuestionDto questionDto = createQuestionDto(22222);

        Optional<QuestionDto> questionDtoOptional = questionService.create(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        QuestionDto toUpdateQuestionDto = new QuestionDto.Builder()
                .setId(savedQuestion.getId())
                .setText("updatedText")
                .setImagePath("new/path")
                .setItem(savedQuestion.getItem())
                .build();
        Optional<QuestionDto> questionDtoOptionalUpdate = questionService.update(toUpdateQuestionDto);

        QuestionDto updatedQuestion = questionDtoOptionalUpdate
                .orElseThrow(() -> new QuestionException("Can't update question with id = " + savedQuestion.getId()));

        QuestionEntity foundQuestion = questionRepository.getById(updatedQuestion.getId());

        equalsQuestions(updatedQuestion, foundQuestion);
    }

    @Test
    void deleteById() {
        QuestionDto questionDto = createQuestionDto(33333);

        Optional<QuestionDto> questionDtoOptional = questionService.create(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        List<QuestionEntity> foundQuestionsBefore = questionRepository.findAll();
        assertEquals(1, foundQuestionsBefore.size());

        Optional<QuestionDto> deletedQuestionOptional = questionService.deleteById(savedQuestion.getId());
        QuestionDto deletedQuestion = deletedQuestionOptional
                .orElseThrow(() -> new QuestionException("Can't delete question with id = " + savedQuestion.getId()));
        equalsQuestions(savedQuestion, deletedQuestion);

        List<QuestionEntity> foundQuestionAfter = questionRepository.findAll();
        assertEquals(0, foundQuestionAfter.size());
    }

    @Test
    void getById() {
        QuestionDto questionDto = createQuestionDto(44444);

        Optional<QuestionDto> questionDtoOptional = questionService.create(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        Optional<QuestionDto> foundQuestionOptional = questionService.getById(savedQuestion.getId());
        QuestionDto foundQuestion = foundQuestionOptional
                .orElseThrow(QuestionNotFoundException::new);

        equalsQuestions(savedQuestion, foundQuestion);
    }

    @Test
    void getAll() {
        List<QuestionDto> questionDtos = IntStream.range(0, 3)
                .mapToObj(i -> createQuestionDto(55555 + i))
                .collect(Collectors.toList());

        List<QuestionDto> savedDtos = questionDtos.stream()
                .map(questionService::create)
                .map(questionDto -> questionDto
                        .orElseThrow(() -> new QuestionException("Can't create question")))
                .collect(Collectors.toList());
        savedDtos.forEach(question -> assertNotNull(question.getId()));

        List<QuestionDto> foundQuestions = questionService.getAll();
        assertEquals(3, foundQuestions.size());

        IntStream.range(0, 3).forEach(i -> equalsQuestions(savedDtos.get(i), foundQuestions.get(i)));
    }

    private void equalsQuestions(QuestionDto expected, QuestionDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getItem(), expected.getItem());
    }

    private void equalsQuestions(QuestionDto expected, QuestionEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getImagePath(), actual.getImagePath());
    }

    private QuestionDto createQuestionDto(int num) {
        ItemDto item = new ItemDto(null, "testItem", "test/path");
        return new QuestionDto.Builder()
                .setText("testQuestion" + num)
                .setImagePath("test/path/quest")
                .setItem(item)
                .build();
    }
}