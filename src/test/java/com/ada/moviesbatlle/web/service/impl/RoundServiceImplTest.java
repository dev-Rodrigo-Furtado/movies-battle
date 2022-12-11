package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.web.service.QuestionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Movie primaryMovie = new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.8, 1_659_591);
        Movie secondaryMovie = new Movie("tt1745960", "Top Gun: Maverick", 8.4, 435_970);

        Question existingQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 8.3, 746_844),
                new Movie("tt8579674", "1917", 8.2, 585_036));

        List<Round> existingRounds = Arrays.asList(
                new Round(existingQuestion));

        Question question = new Question(primaryMovie, secondaryMovie);

        Round expectedRound = new Round(question);

        when(questionService.createQuestionExcept(Arrays.asList(existingQuestion)))
                .thenReturn(question);

        Round round = roundService.createRound(existingRounds);

        assertEquals(expectedRound.getQuestion(), round.getQuestion());
        assertEquals(expectedRound.getStatus(), round.getStatus());
        assertEquals(expectedRound.getResult(), round.getResult());
    }

}