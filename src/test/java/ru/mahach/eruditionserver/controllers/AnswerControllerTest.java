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
import ru.mahach.eruditionserver.entity.AnswerEntity;
import ru.mahach.eruditionserver.services.AnswerService;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AnswerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AnswerService answerService;

    @Test
    void shouldCreateAnswer() throws Exception{
        AnswerEntity createAnswer = createAnswer(null, "TextTest", 1L, true);
        MvcResult mvcResult = mvc.perform(post("/api/v1/answers")
                .content(asJsonString(createAnswer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("text", is(createAnswer.getText())))
                .andExpect(jsonPath("question", is(createAnswer.getQuestionId().intValue())))
                .andExpect(jsonPath("true", is(createAnswer.isTrue())))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Integer createdAnswerId = JsonPath.read(content, "id");
        answerService.deleteAnswerById((long)createdAnswerId);
    }

    @Test
    void shouldUpdateAnswer() throws Exception{
        Optional<AnswerEntity> answerOptional = answerService.createAnswer(createAnswer(null, "TextTest", 1L, true));
        AnswerEntity newAnswer = answerOptional.orElseThrow(IllegalArgumentException::new);
        AnswerEntity updateAnswer = createAnswer(newAnswer.getId(), "TextTestUpdate", 1L, false);

        mvc.perform(put("/api/v1/answers")
                .content(asJsonString(updateAnswer))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(updateAnswer.getId().intValue())))
                .andExpect(jsonPath("text", is(updateAnswer.getText())))
                .andExpect(jsonPath("question", is(updateAnswer.getQuestionId().intValue())))
                .andExpect(jsonPath("true", is(updateAnswer.isTrue())));

        answerService.deleteAnswerById(updateAnswer.getId()).orElseThrow();
    }

    @Test
    void shouldDeleteAnswer() throws Exception{
        AnswerEntity createAnswer = createAnswer(null, "TestText", 1L, true);
        Optional<AnswerEntity> answerOptional = answerService.createAnswer(createAnswer);
        AnswerEntity newAnswer = answerOptional.orElseThrow(IllegalArgumentException::new);
        int id = newAnswer.getId().intValue();

        mvc.perform(delete("/api/v1/answers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)));
    }

    @Test
    void shouldGetAnswerById() throws Exception{
        AnswerEntity createAnswer = createAnswer(null, "TestText", 1L, true);
        Optional<AnswerEntity> answerOptional = answerService.createAnswer(createAnswer);
        AnswerEntity newAnswer = answerOptional.orElseThrow(IllegalArgumentException::new);
        int id = newAnswer.getId().intValue();
        mvc.perform(get("/api/v1/answers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(id)))
                .andExpect(jsonPath("text", is(createAnswer.getText())))
                .andExpect(jsonPath("question", is(createAnswer.getQuestionId().intValue())))
                .andExpect(jsonPath("true", is(createAnswer.isTrue())));
        answerService.deleteAnswerById(newAnswer.getId());
    }

    private AnswerEntity createAnswer(Long id, String text, Long question, Boolean isTrue){
        AnswerEntity answer = new AnswerEntity();
        answer.setId(id);
        answer.setText(text);
        answer.setQuestionId(question);
        answer.setTrue(isTrue);
        return answer;
    }

    static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}