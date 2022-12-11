package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.web.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.ada.moviesbatlle.fixtures.Fixtures.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoundServiceImplTest {

    @InjectMocks
    public RoundServiceImpl roundService;

    @Mock
    public QuestionService questionService;

    @Test
    public void createRound_shouldReturnRound() {
        Question existingQuestion = buildQuestion(
                buildMovie("tt10872600", "Spider-Man: No Way Home"),
                buildMovie("tt8579674", "1917"));

        List<Round> existingRounds = Arrays.asList(
                buildRound(existingQuestion));

        Round expectedRound = buildRound();

        when(questionService.createQuestionExcept(Arrays.asList(existingQuestion)))
                .thenReturn(expectedRound.getQuestion());

        Round round = roundService.createRound(existingRounds);

        assertEquals(expectedRound.getQuestion(), round.getQuestion());
        assertEquals(expectedRound.getStatus(), round.getStatus());
        assertEquals(expectedRound.getResult(), round.getResult());
    }

}