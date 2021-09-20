package ru.mahach.eruditionserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mahach.eruditionserver.models.Question;
import ru.mahach.eruditionserver.services.QuestionService;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private QuestionService questionService;

    @Test
    void shouldCreateQuestion() throws Exception{
        Question createQuestion  = createQuestion(null, "TextTest", 1L, "TestPath");
        MvcResult mvcResult = mvc.perform(post("/api/v1/question")
                .content(asJsonString(createQuestion))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("text", is(createQuestion.getText())))
                .andExpect(jsonPath("item", is(createQuestion.getItem().intValue())))
                .andExpect(jsonPath("imagePath", is(createQuestion.getImagePath())))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Integer createdQuestionId = JsonPath.read(content, "id");
        questionService.deleteQuestionById((long) createdQuestionId);
    }

    @Test
    void shouldUpdateQuestion() throws Exception{
        Optional<Question> questionOptional = questionService.createQuestion(createQuestion(null, "TextTest", 1L, "TestPath"));
        Question newQuestions = questionOptional.orElseThrow(IllegalArgumentException::new);
        Question updateQuestion = createQuestion(newQuestions.getId(), "TextTestUpdate", 2L, "TestPathUpdate");

        mvc.perform(put("/api/v1/question")
                .content(asJsonString(updateQuestion))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateQuestion.getId().intValue())))
                .andExpect(jsonPath("text", is(updateQuestion.getText())))
                .andExpect(jsonPath("item", is(updateQuestion.getItem().intValue())))
                .andExpect(jsonPath("imagePath", is(updateQuestion.getImagePath())));

        questionService.deleteQuestionById(updateQuestion.getId()).orElseThrow();
    }

    @Test
    void shouldDeleteQuestion() throws Exception{
        Question createQuestion = createQuestion(null, "TextTest", 1L, "TestPath");
        Optional<Question> questionOptional = questionService.createQuestion(createQuestion);
        Question newQuestion = questionOptional.orElseThrow(IllegalArgumentException::new);
        int id = newQuestion.getId().intValue();

        mvc.perform(delete("/api/v1/question/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void shouldGetQuestionById() throws Exception{
        Question createQuestion = createQuestion(null, "TextTest", 1L, "TestPath");
        Optional<Question> questionOptional = questionService.createQuestion(createQuestion);
        Question newQuestion = questionOptional.orElseThrow(IllegalArgumentException::new);
        int id = newQuestion.getId().intValue();

        mvc.perform(get("/api/v1/question/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("text", is(createQuestion.getText())))
                .andExpect(jsonPath("item", is(createQuestion.getItem().intValue())))
                .andExpect(jsonPath("imagePath", is(createQuestion.getImagePath())));
        questionService.deleteQuestionById(newQuestion.getId());
    }

    private Question createQuestion(Long id, String text, Long item, String imagePath){
        Question question = new Question();
        question.setId(id);
        question.setText(text);
        question.setItem(item);
        question.setImagePath(imagePath);
        return question;
    }

    static String asJsonString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
}