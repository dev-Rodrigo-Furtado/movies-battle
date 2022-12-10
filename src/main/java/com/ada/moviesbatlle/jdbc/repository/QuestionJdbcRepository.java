package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class QuestionJdbcRepository implements QuestionRepository{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${insert.question.query}")
    private String insertQuestionQuery;

    public void save(Question question, UUID roundID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", question.getID())
                .addValue("round_id", roundID)
                .addValue("primary_movie_imdb_id", question.getPrimaryMovie().getImdbID())
                .addValue("secondary_movie_imdb_id", question.getSecodaryMovie().getImdbID());

        namedParameterJdbcTemplate.update(insertQuestionQuery, parameterSource);
    }

}
