package ru.mahach.eruditionserver.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mahach.eruditionserver.exceptions.AnswerNotFoundException;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.models.dto.ItemDto;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.models.entity.AnswerEntity;
import ru.mahach.eruditionserver.repository.AnswerRepository;

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

    @Autowired
    public AnswerServiceTest(AnswerRepository answerRepository, AnswerService answerService) {
        this.answerRepository = answerRepository;
        this.answerService = answerService;
    }

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
    }

    @Test
    void create() {
        AnswerDto answerDto = createAnswerDto(11111);
        Optional<AnswerDto> answerDtoOptional = answerService.create(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        List<AnswerEntity> foundAnswers = answerRepository.findAll();
        assertEquals(1, foundAnswers.size());

        equalsAnswers(savedAnswer, foundAnswers.get(0));
    }

    @Test
    void update() {
        AnswerDto answerDto = createAnswerDto(22222);

        Optional<AnswerDto> answerDtoOptional = answerService.create(answerDto);
        AnswerDto savedDto = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedDto.getId());

        AnswerDto toUpdateAnswerDto = new AnswerDto(savedDto.getId(), "updatedNameAnswer", true, savedDto.getQuestion());
        Optional<AnswerDto> answerDtoOptionalUpdate = answerService.update(toUpdateAnswerDto);

        AnswerDto updatedAnswer = answerDtoOptionalUpdate
                .orElseThrow(() -> new AnswerException("Can't update item with id = " + savedDto.getId()));

        AnswerEntity foundAnswer = answerRepository.getById(updatedAnswer.getId());

        equalsAnswers(updatedAnswer, foundAnswer);
    }

    @Test
    void deleteById() {
        AnswerDto answerDto = createAnswerDto(33333);

        Optional<AnswerDto> answerDtoOptional = answerService.create(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        List<AnswerEntity> foundAnswersBefore = answerRepository.findAll();
        assertEquals(1, foundAnswersBefore.size());

        Optional<AnswerDto> deletedAnswerOptional = answerService.deleteById(savedAnswer.getId());
        AnswerDto deletedAnswer = deletedAnswerOptional
                .orElseThrow(() -> new AnswerException("Can't delete answer with id = " + savedAnswer.getId()));
        equalsAnswers(savedAnswer, deletedAnswer);

        List<AnswerEntity> foundAnswerAfter = answerRepository.findAll();
        assertEquals(0, foundAnswerAfter.size());
    }

    @Test
    void getById() {
        AnswerDto answerDto = createAnswerDto(44444);

        Optional<AnswerDto> answerDtoOptional = answerService.create(answerDto);
        AnswerDto savedAnswer = answerDtoOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));
        assertNotNull(savedAnswer.getId());

        Optional<AnswerDto> foundAnswerOptional = answerService.getById(savedAnswer.getId());
        AnswerDto foundAnswer = foundAnswerOptional
                .orElseThrow(AnswerNotFoundException::new);

        equalsAnswers(savedAnswer, foundAnswer);
    }

    @Test
    void getAll() {
        List<AnswerDto> answerDtos = IntStream.range(0, 3)
                .mapToObj(i -> createAnswerDto(55555 + i))
                .collect(Collectors.toList());

        List<AnswerDto> savedDtos = answerDtos.stream()
                .map(answerService::create)
                .map(answerDto -> answerDto
                        .orElseThrow(() -> new AnswerException("Can't create answer")))
                .collect(Collectors.toList());
        savedDtos.forEach(answer -> assertNotNull(answer.getId()));

        List<AnswerDto> foundAnswers = answerService.getAll();
        assertEquals(3, foundAnswers.size());

        IntStream.range(0, 3).forEach(i -> equalsAnswers(savedDtos.get(i), foundAnswers.get(i)));
    }

    private void equalsAnswers(AnswerDto expected, AnswerDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.isTrue(), actual.isTrue());
        assertEquals(expected.getQuestion(), actual.getQuestion());
    }

    private void equalsAnswers(AnswerDto expected, AnswerEntity actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.isTrue(), actual.isTrue());
    }

    private AnswerDto createAnswerDto(int num) {
        ItemDto itemDto = new ItemDto(null, "testItem", "test/path");
        QuestionDto questionDto = new QuestionDto.Builder()
                .setText("testQuestion")
                .setImagePath("test/qustpath")
                .setItem(itemDto)
                .build();

        return new AnswerDto(null, "testAnswer" + num, true, questionDto);
    }

}
