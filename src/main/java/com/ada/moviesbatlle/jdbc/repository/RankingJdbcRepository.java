package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Ranking;
import com.ada.moviesbatlle.domain.models.RankingPosition;
import com.ada.moviesbatlle.jdbc.rowmapper.RankingPositionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RankingJdbcRepository implements RankingRepository{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${find.ranking.positions.query}")
    private String findRankingPositionsQuery;

    @Override
    public Ranking getRanking() {
        SqlParameterSource parameterSource = new MapSqlParameterSource();

        List<RankingPosition> rankingPositions = namedParameterJdbcTemplate.query(findRankingPositionsQuery, parameterSource, new RankingPositionRowMapper());
        try {
            return new Ranking(rankingPositions);
        } catch (Exception ex) {
            return new Ranking(new ArrayList<>());
        }

    }

}
