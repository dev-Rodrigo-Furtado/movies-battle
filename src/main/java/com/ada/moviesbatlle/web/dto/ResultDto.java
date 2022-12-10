package com.ada.moviesbatlle.web.dto;

import com.ada.moviesbatlle.domain.enums.Result;

public class ResultDto {

    private Result result;

    public ResultDto(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
