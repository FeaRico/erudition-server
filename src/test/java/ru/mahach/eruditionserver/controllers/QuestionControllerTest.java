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
import ru.mahach.eruditionserver.exceptions.base.QuestionException;
import ru.mahach.eruditionserver.models.dto.QuestionDto;
import ru.mahach.eruditionserver.repository.QuestionRepository;
import ru.mahach.eruditionserver.services.QuestionService;
import ru.mahach.eruditionserver.utils.Utility;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class QuestionControllerTest {
    private final MockMvc mvc;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @Autowired
    QuestionControllerTest(MockMvc mvc, QuestionRepository questionRepository, QuestionService questionService) {
        this.mvc = mvc;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @Test
    void itShouldCreateQuestion() throws Exception {
        QuestionDto createQuestion = Utility.createQuestionDto();

        mvc.perform(post("/api/v1/questions")
                .content(Utility.objectToJsonString(createQuestion))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("text", is(createQuestion.getText())))
                .andExpect(jsonPath("item.id").exists())
                .andExpect(jsonPath("imagePath", is(createQuestion.getImagePath())))
                .andReturn();
    }

    @Test
    void itShouldUpdateQuestion() throws Exception {
        QuestionDto createQuestion = Utility.createQuestionDto();

        Optional<QuestionDto> questionOptional = questionService.createQuestion(createQuestion);
        QuestionDto savedQuestion = questionOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));

        QuestionDto updateQuestion = new QuestionDto.Builder()
                .setId(savedQuestion.getId())
                .setText("newText")
                .setImagePath("new/path")
                .setItem(savedQuestion.getItem())
                .build();

        mvc.perform(put("/api/v1/questions")
                .content(Utility.objectToJsonString(updateQuestion))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateQuestion.getId().intValue())))
                .andExpect(jsonPath("text", is(updateQuestion.getText())))
                .andExpect(jsonPath("item.id", is(updateQuestion.getItem().getId().intValue())))
                .andExpect(jsonPath("imagePath", is(updateQuestion.getImagePath())));
    }

    @Test
    void itShouldDeleteQuestion() throws Exception {
        QuestionDto createQuestion = Utility.createQuestionDto();

        Optional<QuestionDto> questionOptional = questionService.createQuestion(createQuestion);
        QuestionDto savedQuestion = questionOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));

        int id = savedQuestion.getId().intValue();
        mvc.perform(delete("/api/v1/questions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void itShouldGetQuestionById() throws Exception {
        QuestionDto createQuestion = Utility.createQuestionDto();

        Optional<QuestionDto> questionOptional = questionService.createQuestion(createQuestion);
        QuestionDto savedQuestion = questionOptional
                .orElseThrow(() -> new QuestionException("Can't create question"));

        int id = savedQuestion.getId().intValue();
        mvc.perform(get("/api/v1/questions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("text", is(savedQuestion.getText())))
                .andExpect(jsonPath("item.id", is(savedQuestion.getItem().getId().intValue())))
                .andExpect(jsonPath("imagePath", is(savedQuestion.getImagePath())));
    }
}
