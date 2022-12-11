package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizData {

    private String id;
    private RoundData currentRound;
    private int score;
    private int totalCorrectAnswers;
    private int totalWrongAnswers;
    private QuizStatus status;

    public QuizData(String id, RoundData currentRound, int score, int totalCorrectAnswers, int totalWrongAnswers, QuizStatus status) {
        this.id = id;
        this.currentRound = currentRound;
        this.score = score;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
        this.status = status;
    }

    public QuizData(String id, int score, int totalCorrectAnswers, int totalWrongAnswers, QuizStatus status) {
        this.id = id;
        this.score = score;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
        this.status = status;
    }

    public static QuizData fromQuiz(Quiz quiz) {
        Round currentRound = quiz.getCurrentRound();

        if (currentRound != null) {
            RoundData roundData = new RoundData(QuestionData.fromQuestion(currentRound.getQuestion()),
                    currentRound.getResult(),
                    currentRound.getStatus());

            return new QuizData(quiz.getID().toString(), roundData, quiz.getScore(), quiz.getTotalCorrectAnswers(),
                    quiz.getTotalWrongAnswers(), quiz.getStatus());
        }

        return new QuizData(quiz.getID().toString(), quiz.getScore(), quiz.getTotalCorrectAnswers(),
                quiz.getTotalWrongAnswers(), quiz.getStatus());
    }

    public String getId() {
        return id;
    }

    public RoundData getCurrentRound() {
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
