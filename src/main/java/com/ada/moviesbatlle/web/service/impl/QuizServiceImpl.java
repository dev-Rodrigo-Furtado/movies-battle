package com.ada.moviesbatlle.web.service.impl;


import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.web.service.QuizService;
import com.ada.moviesbatlle.web.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private RoundService roundService;

    private final int TOTAL_ROUNDS = 4;

    public Quiz createQuiz() {
        List<Round> rounds = new ArrayList<>();

        while(rounds.size() < TOTAL_ROUNDS) {
            rounds.add(roundService.createRound(rounds));
        }

        return new Quiz(rounds);
    }
}
