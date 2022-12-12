package com.ada.moviesbatlle.web.data;

import com.ada.moviesbatlle.domain.models.Question;

public class QuestionData {

    private String primaryMovie;
    private String secondaryMovie;

    public QuestionData() { }

    public QuestionData(String primaryMovie, String secondaryMovie) {
        this.primaryMovie = primaryMovie;
        this.secondaryMovie = secondaryMovie;
    }

    public static QuestionData fromQuestion(Question question) {
        String primaryMovie = question.getPrimaryMovie().getTitle();
        String secondaryMovie = question.getSecodaryMovie().getTitle();

        return new QuestionData(primaryMovie, secondaryMovie);
    }

    public String getPrimaryMovie() {
        return primaryMovie;
    }

    public String getSecondaryMovie() {
        return secondaryMovie;
    }
}
