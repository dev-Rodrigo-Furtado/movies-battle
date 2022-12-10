package com.ada.moviesbatlle.web.repository;

import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;

import java.util.UUID;

public interface QuizRepository {

    void save(Quiz quiz, UUID loggedUserID);

    void update(Quiz quiz);

    Quiz selectQuiz(UUID quizID, UUID loggedUserID) throws QuizNotFoundException;
}
