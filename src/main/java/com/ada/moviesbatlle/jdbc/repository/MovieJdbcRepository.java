package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.web.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class MovieJdbcRepository implements MovieRepository {

    @Value("${find.random.movie.query}")
    private String findRandomMovieQuery;

    @Value("${find.random.movie.except.query}")
    private String findRandomMovieExceptQuery;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String findRandomMovieImdbID() {
        SqlParameterSource namedParameters = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForObject(findRandomMovieQuery, namedParameters,
                String.class);
    }

    @Override
    public String findRandomMovieImdbIDExcept(String unexpectedImdbID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("imdb_id", unexpectedImdbID);

        return namedParameterJdbcTemplate.queryForObject(findRandomMovieExceptQuery, namedParameters,
                String.class);
    }
}
