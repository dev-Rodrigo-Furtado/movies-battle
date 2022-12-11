package com.ada.moviesbatlle.web.response;

import com.ada.moviesbatlle.web.data.QuestionData;
import com.ada.moviesbatlle.web.data.ResultData;

public class ResultResponse {
    private ResultData data;

    public ResultResponse(ResultData data) {
        this.data = data;
    }

    public ResultData getData() {
        return data;
    }
}

