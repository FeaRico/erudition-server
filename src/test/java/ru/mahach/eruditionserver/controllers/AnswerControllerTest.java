package ru.mahach.eruditionserver.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mahach.eruditionserver.exceptions.base.AnswerException;
import ru.mahach.eruditionserver.models.dto.AnswerDto;
import ru.mahach.eruditionserver.repository.AnswerRepository;
import ru.mahach.eruditionserver.services.AnswerService;
import ru.mahach.eruditionserver.utils.Utility;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AnswerControllerTest {
    private final MockMvc mvc;
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;

    @Autowired
    AnswerControllerTest(MockMvc mvc, AnswerRepository answerRepository, AnswerService answerService) {
        this.mvc = mvc;
        this.answerRepository = answerRepository;
        this.answerService = answerService;
    }

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
    }

    @Test
    void itShouldCreateAnswer() throws Exception {
        AnswerDto createAnswer = Utility.createAnswerDto();

        mvc.perform(post("/api/v1/answers")
                .content(Utility.objectToJsonString(createAnswer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("text", is(createAnswer.getText())))
                .andExpect(jsonPath("question.id").exists())
                .andExpect(jsonPath("isTrue", is(createAnswer.getIsTrue())))
                .andReturn();
    }

    @Test
    void itShouldUpdateAnswer() throws Exception {
        AnswerDto createAnswer = Utility.createAnswerDto();

        Optional<AnswerDto> answerOptional = answerService.createAnswer(createAnswer);
        AnswerDto savedAnswer = answerOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));

        AnswerDto updateAnswer = new AnswerDto(savedAnswer.getId(), "newText",
                false, savedAnswer.getQuestion());

        mvc.perform(put("/api/v1/answers")
                .content(Utility.objectToJsonString(updateAnswer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateAnswer.getId().intValue())))
                .andExpect(jsonPath("text", is(updateAnswer.getText())))
                .andExpect(jsonPath("question.id", is(updateAnswer.getQuestion().getId().intValue())))
                .andExpect(jsonPath("isTrue", is(updateAnswer.getIsTrue())));
    }

    @Test
    void itShouldDeleteAnswer() throws Exception {
        AnswerDto createAnswer = Utility.createAnswerDto();

        Optional<AnswerDto> answerOptional = answerService.createAnswer(createAnswer);
        AnswerDto savedAnswer = answerOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));

        int id = savedAnswer.getId().intValue();
        mvc.perform(delete("/api/v1/answers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void itShouldGetAnswerById() throws Exception {
        AnswerDto createAnswer = Utility.createAnswerDto();

        Optional<AnswerDto> answerOptional = answerService.createAnswer(createAnswer);
        AnswerDto savedAnswer = answerOptional
                .orElseThrow(() -> new AnswerException("Can't create answer"));

        int id = savedAnswer.getId().intValue();
        mvc.perform(get("/api/v1/answers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("text", is(savedAnswer.getText())))
                .andExpect(jsonPath("question.id", is(savedAnswer.getQuestion().getId().intValue())))
                .andExpect(jsonPath("isTrue", is(savedAnswer.getIsTrue())));
    }
}
