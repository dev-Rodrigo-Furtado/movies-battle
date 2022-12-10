package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.web.service.MovieService;
import com.ada.moviesbatlle.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private MovieService movieService;

    public Question createQuestionExcept(List<Question> unexpectedQuestions) {
        Question question = createQuestion();

        if(unexpectedQuestions.contains(question))
            question = createQuestionExcept(unexpectedQuestions);

        return question;
    }

    private Question createQuestion() {
        Movie primaryMovie = movieService.getRandomMovie();
        Movie secodaryMovie = movieService.getRandomMovieExcept(primaryMovie.getImdbID());

        return new Question(primaryMovie, secodaryMovie);
    }
}
