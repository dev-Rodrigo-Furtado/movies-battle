package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.web.response.QuizResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuizControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void startNewQuiz() throws Exception {
       mockMvc.perform(post("/quiz/start")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated());
    }

    @Test
    public void stop_WithStartedQuiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/stop", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isOk());
    }

    @Test
    public void stop_WithNotStartedQuiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/stop", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")));

        mockMvc.perform(post("/quiz/{id}/stop", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message")
                        .value("Quiz does not have STARTED status"));
    }

    @Test
    public void stop_WithNonexistentQuizId() throws Exception {
        mockMvc.perform(post("/quiz/{id}/stop", "9eb2541d-bf6b-463f-ad5f-71508149c84d")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message")
                        .value("Quiz not found, please enter a valid quiz id"));
    }

    @Test
    public void quiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(get("/quiz/{id}", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(quizID.toString()));
    }

    @Test
    public void quiz_WithNonexistentQuizId() throws Exception {
        mockMvc.perform(get("/quiz/{id}", "9eb2541d-bf6b-463f-ad5f-71508149c84d")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message")
                        .value("Quiz not found, please enter a valid quiz id"));
    }

    @Test
    public void getQuestion() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(get("/quiz/{id}/question", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.primaryMovie").exists())
                .andExpect(jsonPath("$.data.secondaryMovie").exists());
    }

    @Test
    public void getQuestion_WithStoppedQuiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/stop", quizID)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")));

        mockMvc.perform(get("/quiz/{id}/question", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message")
                        .value("You have stopped this Quiz, please start a new Quiz to play more."));
    }

    @Test
    public void answerCurrentQuestion_WithFirstAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "FIRST")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").exists());
    }

    @Test
    public void answerCurrentQuestion_WithSecondAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "SECOND")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").exists());
    }

    @Test
    public void answerCurrentQuestion_WithInvalidAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "foo")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("Invalid parameters format"));
    }

    @Test
    public void getRanking() throws Exception {
       mockMvc.perform(get("/quiz/ranking"))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.ranking").exists());

    }

}