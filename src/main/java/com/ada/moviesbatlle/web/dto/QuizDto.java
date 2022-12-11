package com.ada.moviesbatlle.web.dto;

import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizDto {

    private String id;
    private RoundDto currentRound;
    private int score;
    private int totalCorrectAnswers;
    private int totalWrongAnswers;
    private QuizStatus status;

    public QuizDto(String id, RoundDto currentRound, int score, int totalCorrectAnswers, int totalWrongAnswers, QuizStatus status) {
        this.id = id;
        this.currentRound = currentRound;
        this.score = score;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
        this.status = status;
    }

    public QuizDto(String id, int score, int totalCorrectAnswers, int totalWrongAnswers, QuizStatus status) {
        this.id = id;
        this.score = score;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
        this.status = status;
    }

    public static QuizDto fromQuiz(Quiz quiz) {
        Round currentRound = quiz.getCurrentRound();

        if (currentRound != null) {
            RoundDto roundDto = new RoundDto(QuestionDto.fromQuestion(currentRound.getQuestion()),
                    currentRound.getResult(),
                    currentRound.getStatus());

            return new QuizDto(quiz.getID().toString(), roundDto, quiz.getScore(), quiz.getTotalCorrectAnswers(),
                    quiz.getTotalWrongAnswers(), quiz.getStatus());
        }

        return new QuizDto(quiz.getID().toString(), quiz.getScore(), quiz.getTotalCorrectAnswers(),
                quiz.getTotalWrongAnswers(), quiz.getStatus());
    }

    public String getId() {
        return id;
    }

    public RoundDto getCurrentRound() {
        return currentRound;
    }

    public int getScore() {
        return score;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public int getTotalWrongAnswers() {
        return totalWrongAnswers;
    }

    public QuizStatus getStatus() {
        return status;
    }
}
