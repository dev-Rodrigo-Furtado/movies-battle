package com.ada.moviesbatlle.web.service;

import com.ada.moviesbatlle.domain.models.Movie;

public interface MovieService {

    Movie getRandomMovie();
    Movie getRandomMovieExcept(String unexpectedMovieImdbID);
}
