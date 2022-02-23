package ru.mahach.eruditionserver.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mahach.eruditionserver.exceptions.QuestionNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.QuestionEntity;
import ru.mahach.eruditionserver.repository.QuestionRepository;
import ru.mahach.eruditionserver.utils.Utility;

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
    void itShouldCreateQuestion() {
        QuestionDto questionDto = Utility.createQuestionDto();

        Optional<QuestionDto> questionDtoOptional = questionService.createQuestion(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        List<QuestionEntity> foundQuestions = questionRepository.findAll();
        assertEquals(1, foundQuestions.size());

        Utility.equalsQuestions(savedQuestion, foundQuestions.get(0));
    }

    @Test
    void itShouldUpdateQuestion() {
        QuestionDto questionDto = Utility.createQuestionDto();

        Optional<QuestionDto> questionDtoOptional = questionService.createQuestion(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        QuestionDto toUpdateQuestionDto = new QuestionDto.Builder()
                .setId(savedQuestion.getId())
                .setText("updatedText")
                .setImagePath("new/path")
                .setItem(savedQuestion.getItem())
                .build();
        Optional<QuestionDto> questionDtoOptionalUpdate = questionService.updateQuestion(toUpdateQuestionDto);

        QuestionDto updatedQuestion = questionDtoOptionalUpdate
                .orElseThrow(() -> new QuestionException("Can't update question with id = " + savedQuestion.getId()));

        QuestionEntity foundQuestion = questionRepository.getById(updatedQuestion.getId());

        Utility.equalsQuestions(updatedQuestion, foundQuestion);
    }

    @Test
    void itShouldDeleteQuestionById() {
        QuestionDto questionDto = Utility.createQuestionDto();

        Optional<QuestionDto> questionDtoOptional = questionService.createQuestion(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        List<QuestionEntity> foundQuestionsBefore = questionRepository.findAll();
        assertEquals(1, foundQuestionsBefore.size());

        Optional<QuestionDto> deletedQuestionOptional = questionService.deleteQuestionById(savedQuestion.getId());
        QuestionDto deletedQuestion = deletedQuestionOptional
                .orElseThrow(() -> new QuestionException("Can't delete question with id = " + savedQuestion.getId()));
        Utility.equalsQuestions(savedQuestion, deletedQuestion);

        List<QuestionEntity> foundQuestionAfter = questionRepository.findAll();
        assertEquals(0, foundQuestionAfter.size());
    }

    @Test
    void itShouldGetQuestionById() {
        QuestionDto questionDto = Utility.createQuestionDto();

        Optional<QuestionDto> questionDtoOptional = questionService.createQuestion(questionDto);
        QuestionDto savedQuestion = questionDtoOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));
        assertNotNull(savedQuestion.getId());

        Optional<QuestionDto> foundQuestionOptional = questionService.getQuestionById(savedQuestion.getId());
        QuestionDto foundQuestion = foundQuestionOptional
                .orElseThrow(QuestionNotFoundException::new);

        Utility.equalsQuestions(savedQuestion, foundQuestion);
    }

    @Test
    void itShouldGetAllQuestions() {
        List<QuestionDto> questionDtos = IntStream.range(0, 3)
                .mapToObj(i -> Utility.createQuestionDto())
                .collect(Collectors.toList());

        List<QuestionDto> savedDtos = questionDtos.stream()
                .map(questionService::createQuestion)
                .map(questionDto -> questionDto
                        .orElseThrow(() -> new QuestionException("Can't create question")))
                .collect(Collectors.toList());
        savedDtos.forEach(question -> assertNotNull(question.getId()));

        List<QuestionDto> foundQuestions = questionService.getAllQuestions();
        assertEquals(3, foundQuestions.size());

        IntStream.range(0, 3).forEach(i -> Utility.equalsQuestions(savedDtos.get(i), foundQuestions.get(i)));
    }
}