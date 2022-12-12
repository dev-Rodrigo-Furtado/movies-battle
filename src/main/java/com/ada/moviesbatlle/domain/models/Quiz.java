package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.domain.exceptions.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


public class Quiz {
    private UUID id;
    private List<Round> rounds;
    private Round currentRound;
    private int score;
    private int totalCorrectAnswers;
    private int totalWrongAnswers;
    private QuizStatus status;
    private static final int MAX_WRONG_ANSWERS = 3;

    public Quiz(List<Round> rounds) {
        this.id = UUID.randomUUID();
        this.rounds = rounds;
        this.status = QuizStatus.STARTED;

        this.currentRound = rounds.stream()
                .filter(r -> r.getStatus() == RoundStatus.PENDING)
                .findFirst()
                .get();

        this.currentRound.makeCurrent();
    }

    public Quiz(UUID id, List<Round> rounds, QuizStatus status, int score, int totalCorrectAnswers, int totalWrongAnswers) {
        this.id = id;
        this.rounds = rounds;
        this.status = status;
        this.currentRound = rounds.stream()
                .filter(r -> r.getStatus() == RoundStatus.CURRENT)
                .findFirst()
                .get();
        this.score = score;
        this.totalCorrectAnswers= totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
    }

    public Result answerCurrentRound(Answer answer) throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Result roundResult = currentRound.answer(answer);

        if(roundResult == Result.CORRECT_ANSWER) {
            score++;
            totalCorrectAnswers++;
        } else {
            totalWrongAnswers++;
        }

        if(totalWrongAnswers == MAX_WRONG_ANSWERS) {
            status = QuizStatus.DEFEAT;
        }

        return roundResult;
    }

    public void nextCurrentRound() {

        if(!currentRound.isAnswerd())
            return;

        Optional<Round> round = rounds.stream()
                .filter(r -> r.getStatus() == RoundStatus.PENDING)
                .findFirst();

        if(round.isEmpty()) {
            status = QuizStatus.COMPLETED;
            return;
        }

        currentRound.makeDone();

        this.currentRound = round.get();
        currentRound.makeCurrent();
    }

    public void stop() throws QuizNotStartedException {
        if(this.status != QuizStatus.STARTED)
            throw new QuizNotStartedException();

        this.status = QuizStatus.STOPPED;
    }

    public UUID getID() {
        return id;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Round> getRounds() {
        return rounds;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (score != quiz.score) return false;
        if (totalCorrectAnswers != quiz.totalCorrectAnswers) return false;
        if (totalWrongAnswers != quiz.totalWrongAnswers) return false;
        if (!Objects.equals(rounds, quiz.rounds)) return false;
        if (!Objects.equals(currentRound, quiz.currentRound)) return false;
        return status == quiz.status;
    }
}
