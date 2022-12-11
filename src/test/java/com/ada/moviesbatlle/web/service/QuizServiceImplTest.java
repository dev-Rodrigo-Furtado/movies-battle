package com.ada.moviesbatlle.web.service;

import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.web.service.impl.QuestionServiceImpl;
import com.ada.moviesbatlle.web.service.impl.QuizServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static com.ada.moviesbatlle.fixtures.Fixtures.buildQuiz;
import static com.ada.moviesbatlle.fixtures.Fixtures.buildRound;

@RunWith(MockitoJUnitRunner.class)
public class QuizServiceImplTest {

    @InjectMocks
    public QuizServiceImpl quizService;

    @Mock
    public RoundService roundService;

    @Test
    public void createQuiz_shouldReturnQuiz() {
        Round round = buildRound();
        Quiz expectedQuiz = buildQuiz(round);

        Mockito.when(roundService.createRound(new ArrayList<>())).thenReturn(buildRound());
        Quiz quiz = quizService.createQuiz(1);

        Assert.assertEquals(expectedQuiz, quiz);
    }


}