package com.ada.moviesbatlle.jdbc.entity;

import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;

import java.util.UUID;

public class QuizEntity {

    private UUID quizUUID;
    private int quizScore;
    private int quizTotalWrongAnswers;
    private QuizStatus quizStatus;

    private UUID roundUUID;
    private Result roundResult;
    private RoundStatus roundStatus;

    private UUID questionUUID;
    private String questionPrimaryMovieID;
    private String questionSecondaryMovieID;

    public UUID getQuizUUID() {
        return quizUUID;
    }

    public void setQuizUUID(UUID quizUUID) {
        this.quizUUID = quizUUID;
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }

    public int getQuizTotalWrongAnswers() {
        return quizTotalWrongAnswers;
    }

    public void setQuizTotalWrongAnswers(int quizTotalWrongAnswers) {
        this.quizTotalWrongAnswers = quizTotalWrongAnswers;
    }

    public QuizStatus getQuizStatus() {
        return quizStatus;
    }

    public void setQuizStatus(QuizStatus quizStatus) {
        this.quizStatus = quizStatus;
    }

    public UUID getRoundUUID() {
        return roundUUID;
    }

    public void setRoundUUID(UUID roundUUID) {
        this.roundUUID = roundUUID;
    }

    public Result getRoundResult() {
        return roundResult;
    }

    public void setRoundResult(Result roundResult) {
        this.roundResult = roundResult;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public UUID getQuestionUUID() {
        return questionUUID;
    }

    public void setQuestionUUID(UUID questionUUID) {
        this.questionUUID = questionUUID;
    }

    public String getQuestionPrimaryMovieID() {
        return questionPrimaryMovieID;
    }

    public void setQuestionPrimaryMovieID(String questionPrimaryMovieID) {
        this.questionPrimaryMovieID = questionPrimaryMovieID;
    }

    public String getQuestionSecondaryMovieID() {
        return questionSecondaryMovieID;
    }

    public void setQuestionSecondaryMovieID(String questionSecondaryMovieID) {
        this.questionSecondaryMovieID = questionSecondaryMovieID;
    }
}
