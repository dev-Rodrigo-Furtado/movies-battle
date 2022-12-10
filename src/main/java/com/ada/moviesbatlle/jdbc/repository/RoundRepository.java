package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Round;

import java.util.List;
import java.util.UUID;

public interface RoundRepository {

    void save(Round round, UUID quizID);

    void saveList(List<Round> rounds, UUID quizID);

    void update(Round round);

    void updateList(List<Round> rounds);
}
