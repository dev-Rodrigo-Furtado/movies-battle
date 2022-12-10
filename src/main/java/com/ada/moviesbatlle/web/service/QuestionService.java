package com.ada.moviesbatlle.web.service;

import com.ada.moviesbatlle.domain.models.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestionExcept(List<Question> unexpectedQuestions);
}
