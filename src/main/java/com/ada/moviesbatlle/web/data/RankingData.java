package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.models.Ranking;

import java.util.List;
import java.util.stream.Collectors;

public class RankingData {
    private List<RankingPositionData> ranking;

    public RankingData(List<RankingPositionData> ranking) {
        this.ranking = ranking;
    }

    public static RankingData fromRanking(Ranking ranking) {
        List<RankingPositionData> rankingPositionDataList = ranking.getPositions()
                .stream().map(position -> RankingPositionData.fromRankingPosition(position))
                .collect(Collectors.toList());

        return new RankingData(rankingPositionDataList);
    }

    public List<RankingPositionData> getRanking() {
        return ranking;
    }
}
