package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundData {
    private QuestionData question;
    private Result result;
    private RoundStatus status;

    public RoundData(QuestionData question, Result result, RoundStatus status) {
        this.question = question;
        this.result = result;
        this.status = status;
    }

    public QuestionData getQuestion() {
        return question;
    }

    public Result getResult() {
        return result;
    }

    public RoundStatus getStatus() {
        return status;
    }
}
