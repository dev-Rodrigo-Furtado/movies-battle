package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.domain.exceptions.RoundAlreadyAnswerdException;

import java.util.UUID;

public class Round {
    private UUID id;
    private Question question;
    private Result result;
    private RoundStatus status;

    public Round(Question question) {
        this.id = UUID.randomUUID();
        this.question = question;
        this.status = RoundStatus.PENDING;
        this.result = Result.PENDING;
    }

    public Round(UUID id, Question question, RoundStatus status, Result result) {
        this.id = id;
        this.question = question;
        this.status = status;
        this.result = result;
    }

    public Result answer(Answer answer) throws RoundAlreadyAnswerdException {
        if(isAnswerd())
            throw new RoundAlreadyAnswerdException();

        result = answer == question.getAnswer() ? Result.CORRECT_ANSWER : Result.WRONG_ANSWER;

        return result;
    }

    public void makeCurrent() {
        this.status = RoundStatus.CURRENT;
    }

    public void makeDone() {
        this.status = RoundStatus.DONE;
    }

    public boolean isAnswerd() {
        return result != Result.PENDING;
    }

    public UUID getID() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public Result getResult() {
        return result;
    };

    public RoundStatus getStatus() {
        return status;
    }
}
