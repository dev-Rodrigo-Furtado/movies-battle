package com.ada.moviesbatlle.omdb.response;

import com.ada.moviesbatlle.domain.models.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OmdbMovieResponse {

    @JsonProperty("Title")
    private String title;
    private String imdbID;
    private String imdbVotes;
    private String imdbRating;

    public String getTitle() {
        return title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public Movie toModel() {
        int imdbVotes = Integer.parseInt(getImdbVotes().replaceAll(",",""));
        double imdbRating = Double.parseDouble(getImdbRating());

        return new Movie(imdbID, title, imdbRating, imdbVotes);
    }
}
