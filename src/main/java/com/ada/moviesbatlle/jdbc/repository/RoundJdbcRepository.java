package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RoundJdbcRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private QuestionJdbcRepository questionJdbcRepository;

    @Value("${insert.round.query}")
    private String insertRoundQuery;

    @Value("${update.round.by.id}")
    private String updateRoundByIDQuery;

    public void save(Round round, UUID quizID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", round.getID())
                .addValue("quiz_id", quizID)
                .addValue("result", round.getResult().toString())
                .addValue("status", round.getStatus().toString());

        namedParameterJdbcTemplate.update(insertRoundQuery, parameterSource);
        questionJdbcRepository.save(round.getQuestion(), round.getID());
    }

    public void saveList(List<Round> rounds, UUID quizID) {
        rounds.forEach(r -> save(r, quizID));
    }

    public void update(Round round) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", round.getID())
                .addValue("result", round.getResult().toString())
                .addValue("status", round.getStatus().toString());

        namedParameterJdbcTemplate.update(updateRoundByIDQuery, parameterSource);
    }

    public void updateList(List<Round> rounds) {
        rounds.forEach(r -> update(r));
    }
}
