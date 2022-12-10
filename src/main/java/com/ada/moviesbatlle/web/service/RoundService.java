package com.ada.moviesbatlle.web.service;

import com.ada.moviesbatlle.domain.models.Round;

import java.util.List;

public interface RoundService {
    Round createRound(List<Round> currentRounds);
}
