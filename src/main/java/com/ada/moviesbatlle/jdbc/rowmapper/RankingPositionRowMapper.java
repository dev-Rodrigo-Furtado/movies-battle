package com.ada.moviesbatlle.jdbc.rowmapper;

import com.ada.moviesbatlle.domain.models.RankingPosition;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankingPositionRowMapper implements RowMapper<RankingPosition> {
    @Override
    public RankingPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
        String username = rs.getString("users.username");
        double rankingScore = rs.getDouble("ranking_score");

        return new RankingPosition(username, rankingScore);
    }
}
