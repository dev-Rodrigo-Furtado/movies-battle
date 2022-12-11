package com.ada.moviesbatlle.domain.models;

import java.util.List;

public class Ranking {
    private List<RankingPosition> positions;

    public Ranking(List<RankingPosition> positions) {
        this.positions = positions;
    }

    public List<RankingPosition> getPositions() {
        return positions;
    }
}
