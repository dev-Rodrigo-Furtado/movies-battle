package com.ada.moviesbatlle.domain.models;

import java.util.Objects;

public class Movie {
    private String imdbID;
    private String title;
    private double imdbRating;
    private int imdbVotes;

    public Movie(String imdbID, String title, double imdbRating, int imdbVotes) {
        this.imdbID = imdbID;
        this.title = title;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getTitle() {
        return title;
    }

    public double getScore() {
        return imdbRating * imdbVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!Objects.equals(title, movie.title)) return false;
        if (!Objects.equals(imdbRating, movie.imdbRating)) return false;
        return Objects.equals(imdbVotes, movie.imdbVotes);
    }
}
