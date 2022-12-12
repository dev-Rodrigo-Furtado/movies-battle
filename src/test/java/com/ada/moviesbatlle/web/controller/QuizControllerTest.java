package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.web.response.QuizResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class QuizControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void startNewQuiz() throws Exception {
        MvcResult result = mockMvc.perform(post("/quiz/start")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("player01", "102030")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

}