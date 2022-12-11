package com.ada.moviesbatlle.fixtures;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;

import java.util.Arrays;

public class Fixtures {
    private Fixtures() {

    }

    public static Movie buildMovie(String imdbID, String title) {
        return new Movie(imdbID, title, 9.0, 1_000_000);
    }

    public static Question buildQuestion(Movie primaryMovie, Movie secondaryMovie) {
        return new Question(primaryMovie, secondaryMovie);
    }

    public static Question buildQuestion() {
        return buildQuestion(
                buildMovie("tt0167261", "The Lord of the Rings: The Two Towers"),
                buildMovie("tt1745960", "Top Gun: Maverick"));
    }

    public static Round buildRound(Question question) {
        return new Round(question);
    }

    public static Round buildRound() {
        Question question = buildQuestion(
                buildMovie("tt0167261", "The Lord of the Rings: The Two Towers"),
                buildMovie("tt1745960", "Top Gun: Maverick"));

        return buildRound(question);
    }

    public static Quiz buildQuiz(Round round) {
        return new Quiz(Arrays.asList(round));
    }

}
