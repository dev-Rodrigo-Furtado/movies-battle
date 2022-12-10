package com.ada.moviesbatlle.web.dto;

import com.ada.moviesbatlle.domain.models.Question;

public class QuestionDto {

    private String primaryMovie;
    private String secondaryMovie;

    public QuestionDto(String primaryMovie, String secondaryMovie) {
        this.primaryMovie = primaryMovie;
        this.secondaryMovie = secondaryMovie;
    }

    public static QuestionDto fromQuestion(Question question) {
        String primaryMovie = question.getPrimaryMovie().getTitle();
        String secondaryMovie = question.getSecodaryMovie().getTitle();

        return new QuestionDto(primaryMovie, secondaryMovie);
    }

    public String getPrimaryMovie() {
        return primaryMovie;
    }

    public String getSecondaryMovie() {
        return secondaryMovie;
    }
}
