package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.enums.Result;

public class ResultData {

    private Result result;

    public ResultData(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
