package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.domain.exceptions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class Quiz {
    private UUID id;
    private List<Round> rounds;
    private Round currentRound;
    private int score;
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

    public Quiz(UUID id, List<Round> rounds, QuizStatus status, int score, int totalWrongAnswers) {
        this.id = id;
        this.rounds = rounds;
        this.status = status;
        this.currentRound = rounds.stream()
                .filter(r -> r.getStatus() == RoundStatus.CURRENT)
                .findFirst()
                .get();
        this.score = score;
        this.totalWrongAnswers = totalWrongAnswers;
    }

    public Result answerCurrentRound(Answer answer) throws RoundAlreadyAnswerdException, NoMoreRoundsException,
            MaxWrongAnswersException, QuizStoppedException {

        checkQuizAvailability();

        Result roundResult = currentRound.answer(answer);

        if(roundResult == Result.CORRECT_ANSWER)
            score++;
        else
            totalWrongAnswers++;

        if(totalWrongAnswers == MAX_WRONG_ANSWERS) {
            status = QuizStatus.DEFEAT;
        }

        return roundResult;
    }

    public Round nextCurrentRound() throws NoMoreRoundsException, MaxWrongAnswersException, QuizStoppedException {
        checkQuizAvailability();

        if(!currentRound.isAnswerd())
            return currentRound;

        Optional<Round> round = rounds.stream()
                .filter(r -> r.getStatus() == RoundStatus.PENDING)
                .findFirst();

        if(round.isPresent()) {
            currentRound.makeDone();

            this.currentRound = round.get();
            currentRound.makeCurrent();

            return currentRound;
        }

        status = QuizStatus.COMPLETED;
        throw new NoMoreRoundsException();
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

    public int getTotalWrongAnswers() {
        return totalWrongAnswers;
    }

    public QuizStatus getStatus() {
        return status;
    }

    private void checkQuizAvailability() throws MaxWrongAnswersException, NoMoreRoundsException, QuizStoppedException {
        if(status == QuizStatus.DEFEAT)
            throw new MaxWrongAnswersException();

        if(status == QuizStatus.COMPLETED)
            throw new NoMoreRoundsException();

        if(status == QuizStatus.STOPPED)
            throw new QuizStoppedException();
    }

}
