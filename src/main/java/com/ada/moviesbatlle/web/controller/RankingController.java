package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Ranking;
import com.ada.moviesbatlle.domain.models.RankingPosition;
import com.ada.moviesbatlle.jdbc.repository.RankingRepository;
import com.ada.moviesbatlle.web.dto.QuizDto;
import com.ada.moviesbatlle.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingController {

    @Autowired
    private RankingRepository rankingRepository;

    @PostMapping("ranking")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Ranking> getRanking() {
        return new Response<>(rankingRepository.getRanking());
    }

}
