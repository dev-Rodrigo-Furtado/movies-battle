package com.ada.moviesbatlle.domain.models;


import com.ada.moviesbatlle.domain.enums.Answer;

import java.util.Objects;
import java.util.UUID;

public class Question {

    private UUID id;
    private Movie primaryMovie;
    private Movie secodaryMovie;

    public Question(Movie primaryMovie, Movie secodaryMovie) {
        this.id = UUID.randomUUID();
        this.primaryMovie = primaryMovie;
        this.secodaryMovie = secodaryMovie;
    }

    public Question(UUID id, Movie primaryMovie, Movie secodaryMovie) {
        this.id = id;
        this.primaryMovie = primaryMovie;
        this.secodaryMovie = secodaryMovie;
    }

    public UUID getID() {
        return id;
    }

    public Movie getPrimaryMovie() {
        return primaryMovie;
    }

    public Movie getSecodaryMovie() {
        return secodaryMovie;
    }

    public Answer getAnswer() {
        if(primaryMovie.getScore() > secodaryMovie.getScore())
            return Answer.FIRST;

        return Answer.SECOND;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if(Objects.equals(primaryMovie, question.primaryMovie))
            return Objects.equals(secodaryMovie, question.secodaryMovie);

        if(Objects.equals(primaryMovie, question.secodaryMovie))
            return Objects.equals(secodaryMovie, question.primaryMovie);

        return false;
    }

}
