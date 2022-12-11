package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.models.RankingPosition;

public class RankingPositionData {

    private String username;
    private double score;

    public RankingPositionData(String username, double score) {
        this.username = username;
        this.score = score;
    }

    public static RankingPositionData fromRankingPosition(RankingPosition rankingPosition) {
        return new RankingPositionData(rankingPosition.getUsername(), rankingPosition.getScore());
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }
}
