package com.ada.moviesbatlle.domain.models;

public class RankingPosition {

    private String username;
    private double score;

    public RankingPosition(String username, double score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }
}
