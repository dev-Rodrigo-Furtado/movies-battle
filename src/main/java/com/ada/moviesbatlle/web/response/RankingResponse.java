package com.ada.moviesbatlle.web.response;

import com.ada.moviesbatlle.web.data.RankingData;

public class RankingResponse {

    private RankingData data;

    public RankingResponse(RankingData data) {
        this.data = data;
    }

    public RankingData getData() {
        return data;
    }
}
