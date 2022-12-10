package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.web.service.QuestionService;
import com.ada.moviesbatlle.web.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    private QuestionService questionService;

    public Round createRound(List<Round> answerdRounds) {
        List<Question> answerdQuestions = answerdRounds
                .stream().map(round -> round.getQuestion()).collect(Collectors.toList());

        Question question = questionService.createQuestionExcept(answerdQuestions);
        return new Round(question);
    }
}
