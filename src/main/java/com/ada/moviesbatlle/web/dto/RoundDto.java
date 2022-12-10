package com.ada.moviesbatlle.web.dto;

import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundDto {
    private QuestionDto question;
    private Result result;
    private RoundStatus status;

    public RoundDto(QuestionDto question, Result result, RoundStatus status) {
        this.question = question;
        this.result = result;
        this.status = status;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public Result getResult() {
        return result;
    }

    public RoundStatus getStatus() {
        return status;
    }
}
