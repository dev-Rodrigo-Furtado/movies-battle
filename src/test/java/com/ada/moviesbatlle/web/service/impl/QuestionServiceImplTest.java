package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.web.service.MovieService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    @InjectMocks
    public QuestionServiceImpl questionService;

    @Mock
    public MovieService movieService;

    @Test
    public void createQuestionExcept_shouldReturnQuestion() {
        Movie primaryMovie = new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.8, 1_659_591);
        Movie secondaryMovie = new Movie("tt1745960", "Top Gun: Maverick", 8.4, 435_970);

        Question expectedQuestion = new Question(primaryMovie, secondaryMovie);

        Mockito.when(movieService.getRandomMovie())
                .thenReturn(primaryMovie);

        Mockito.when(movieService.getRandomMovieExcept("tt0167261"))
                .thenReturn(secondaryMovie);

        Question question = questionService.createQuestionExcept(new ArrayList<>());

        Assert.assertEquals(expectedQuestion, question);
    }

    @Test
    public void createQuestionExcept_shouldReturnQuestionWhenUnexpectedQuestionIsFound() {
        Movie primaryMovie = new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.8, 1_659_591);
        Movie secondaryMovie = new Movie("tt1745960", "Top Gun: Maverick", 8.4, 435_970);

        Question expectedQuestion = new Question(primaryMovie, secondaryMovie);

        Movie primaryUnexpectedMovie =  new Movie("tt10872600", "Spider-Man: No Way Home", 8.3, 746_844);
        Movie secondaryUnexpectedMovie =  new Movie("tt8579674", "1917", 8.2, 585_036);
        Question unexpectedQuestion = new Question(primaryUnexpectedMovie, secondaryUnexpectedMovie);
        List<Question> unexpectedQuestions = Arrays.asList(unexpectedQuestion);

        Mockito.when(movieService.getRandomMovie())
                .thenAnswer(new Answer() {
                    private int count = 0;

                    public Object answer(InvocationOnMock invocation) {
                        if (++count == 1)
                            return primaryUnexpectedMovie;

                        return primaryMovie;
                    }
                });

        Mockito.when(movieService.getRandomMovieExcept(Mockito.any()))
                .thenAnswer(new Answer() {
                    private int count = 0;

                    public Object answer(InvocationOnMock invocation) {
                        if (++count == 1)
                            return secondaryUnexpectedMovie;

                        return secondaryMovie;
                    }
                });

        Question question = questionService.createQuestionExcept(unexpectedQuestions);

        Assert.assertEquals(expectedQuestion, question);
    }

}