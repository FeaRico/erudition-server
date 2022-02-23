package ru.mahach.eruditionserver.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.exceptions.base.ItemException;
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;
import ru.mahach.eruditionserver.repository.AnswerRepository;
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
class AnswerServiceTest {
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final ItemService itemService;

    @Autowired
    public AnswerServiceTest(AnswerRepository answerRepository, AnswerService answerService, QuestionService questionService, ItemService itemService) {
        this.answerRepository = answerRepository;
        this.answerService = answerService;
        this.questionService = questionService;
        this.itemService = itemService;
    }

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
    }

    @Test
    void itShouldCreateAnswer() {
        AnswerDto answerDto = Utility.createAnswerDto(saveQuestionAndReturn());
        Optional<AnswerDto> answerDtoOptional = answerService.createAnswer(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        List<AnswerEntity> foundAnswers = answerRepository.findAll();
        assertEquals(1, foundAnswers.size());

        Utility.equalsAnswers(savedAnswer, foundAnswers.get(0));
    }

    @Test
    void itShouldUpdateAnswer() {
        AnswerDto answerDto = Utility.createAnswerDto(saveQuestionAndReturn());

        Optional<AnswerDto> answerDtoOptional = answerService.createAnswer(answerDto);
        AnswerDto savedDto = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedDto.getId());

        AnswerDto toUpdateAnswerDto = new AnswerDto(savedDto.getId(), "updatedNameAnswer", true, savedDto.getQuestion());
        Optional<AnswerDto> answerDtoOptionalUpdate = answerService.updateAnswer(toUpdateAnswerDto);

        AnswerDto updatedAnswer = answerDtoOptionalUpdate
                .orElseThrow(() -> new AnswerException("Can't update item with id = " + savedDto.getId()));

        AnswerEntity foundAnswer = answerRepository.getById(updatedAnswer.getId());

        Utility.equalsAnswers(updatedAnswer, foundAnswer);
    }

    @Test
    void itShouldDeleteAnswerById() {
        AnswerDto answerDto = Utility.createAnswerDto(saveQuestionAndReturn());

        Optional<AnswerDto> answerDtoOptional = answerService.createAnswer(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        List<AnswerEntity> foundAnswersBefore = answerRepository.findAll();
        assertEquals(1, foundAnswersBefore.size());

        Optional<AnswerDto> deletedAnswerOptional = answerService.deleteAnswerById(savedAnswer.getId());
        AnswerDto deletedAnswer = deletedAnswerOptional
                .orElseThrow(() -> new AnswerException("Can't delete answer with id = " + savedAnswer.getId()));
        Utility.equalsAnswers(savedAnswer, deletedAnswer);

        List<AnswerEntity> foundAnswerAfter = answerRepository.findAll();
        assertEquals(0, foundAnswerAfter.size());
    }

    @Test
    void itShouldGetAnswerById() {
        AnswerDto answerDto = Utility.createAnswerDto(saveQuestionAndReturn());

        Optional<AnswerDto> answerDtoOptional = answerService.createAnswer(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        Optional<AnswerDto> foundAnswerOptional = answerService.getAnswerById(savedAnswer.getId());
        AnswerDto foundAnswer = foundAnswerOptional
                .orElseThrow(AnswerNotFoundException::new);

        Utility.equalsAnswers(savedAnswer, foundAnswer);
    }

    @Test
    void itShouldGetAllAnswers() {
        List<AnswerDto> answerDtos = IntStream.range(0, 3)
                .mapToObj(i -> Utility.createAnswerDto(saveQuestionAndReturn()))
                .collect(Collectors.toList());

        List<AnswerDto> savedDtos = answerDtos.stream()
                .map(answerService::createAnswer)
                .map(answerDto -> answerDto
                        .orElseThrow(() -> new AnswerException("Can't create answer")))
                .collect(Collectors.toList());
        savedDtos.forEach(answer -> assertNotNull(answer.getId()));

        List<AnswerDto> foundAnswers = answerService.getAllAnswers();
        assertEquals(3, foundAnswers.size());

        IntStream.range(0, 3).forEach(i -> Utility.equalsAnswers(savedDtos.get(i), foundAnswers.get(i)));
    }

    private QuestionDto saveQuestionAndReturn(){
        Optional<ItemDto> saveItemOptional = itemService.createItem(Utility.createItemDto());
        ItemDto savedItem = saveItemOptional
                .orElseThrow(() -> new ItemException("Can't save item"));
        Optional<QuestionDto> saveQuestionOptional = questionService
                .createQuestion(Utility.createQuestionDto(savedItem));

        return saveQuestionOptional
                .orElseThrow(() -> new QuestionException("Can't save question"));
    }
}
