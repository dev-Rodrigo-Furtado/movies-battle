package com.ada.moviesbatlle.web.repository;

public interface MovieRepository {

    String findRandomMovieImdbID();
    String findRandomMovieImdbIDExcept(String unexpectedImdbID);
}
