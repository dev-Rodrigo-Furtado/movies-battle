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
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void stop_WithStartedQuiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/stop", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void quiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(get("/quiz/{id}", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(quizID.toString()));
    }

    @Test
    public void getQuestion() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(get("/quiz/{id}/question", quizID)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.primaryMovie").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.secondaryMovie").exists());
    }

    @Test
    public void answerCurrentQuestion_WithFirstAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "FIRST")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.result").exists());
    }

    @Test
    public void answerCurrentQuestion_WithSecondAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "SECOND")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.result").exists());
    }

    @Test
    public void answerCurrentQuestion_WithInvalidAnswer() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        QuizResponse quiz = new ObjectMapper().readValue(json, QuizResponse.class);

        UUID quizID = UUID.fromString(quiz.getData().getId());

        mockMvc.perform(post("/quiz/{id}/answer", quizID)
                        .param("a", "foo")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("Invalid parameters format"));
    }

}