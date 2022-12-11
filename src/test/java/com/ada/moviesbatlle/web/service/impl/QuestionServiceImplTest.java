package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.web.service.MovieService;
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

import static com.ada.moviesbatlle.fixtures.Fixtures.buildMovie;
import static com.ada.moviesbatlle.fixtures.Fixtures.buildQuestion;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    @InjectMocks
    public QuestionServiceImpl questionService;

    @Mock
    public MovieService movieService;

    @Test
    public void createQuestionExcept_shouldReturnQuestion() {
        Question expectedQuestion = buildQuestion();

        when(movieService.getRandomMovie())
                .thenReturn(expectedQuestion.getPrimaryMovie());

        when(movieService.getRandomMovieExcept("tt0167261"))
                .thenReturn(expectedQuestion.getSecodaryMovie());

        Question question = questionService.createQuestionExcept(new ArrayList<>());

        assertEquals(expectedQuestion, question);
    }

    @Test
    public void createQuestionExcept_shouldReturnQuestionWhenUnexpectedQuestionIsFound() {
        Question expectedQuestion = buildQuestion();
        Question unexpectedQuestion = buildQuestion(
                buildMovie("tt10872600", "Spider-Man: No Way Home"),
                buildMovie("tt8579674", "1917"));

        List<Question> unexpectedQuestions = Arrays.asList(unexpectedQuestion);

        when(movieService.getRandomMovie())
                .thenAnswer(new Answer() {
                    private int count = 0;

                    public Object answer(InvocationOnMock invocation) {
                        if (++count == 1)
                            return unexpectedQuestion.getPrimaryMovie();

                        return expectedQuestion.getPrimaryMovie();
                    }
                });

        when(movieService.getRandomMovieExcept(Mockito.any()))
                .thenAnswer(new Answer() {
                    private int count = 0;

                    public Object answer(InvocationOnMock invocation) {
                        if (++count == 1)
                            return unexpectedQuestion.getSecodaryMovie();

                        return expectedQuestion.getSecodaryMovie();
                    }
                });

        Question question = questionService.createQuestionExcept(unexpectedQuestions);

        assertEquals(expectedQuestion, question);
    }

}