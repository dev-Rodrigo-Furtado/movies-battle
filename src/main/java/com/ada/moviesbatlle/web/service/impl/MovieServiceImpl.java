package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.omdb.client.OmdbHttpClient;
import com.ada.moviesbatlle.omdb.client.OmdbHttpClientImpl;
import com.ada.moviesbatlle.web.repository.MovieRepository;
import com.ada.moviesbatlle.web.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private OmdbHttpClient omdbHttpClient;

    @Autowired
    private MovieRepository movieRepository;

    public Movie getRandomMovie() {
        String imdbID = movieRepository.findRandomMovieImdbID();

        return omdbHttpClient.findMovie(imdbID);
    }

    public Movie getRandomMovieExcept(String unexpectedMovieImdbID) {
        String imdbID = movieRepository.findRandomMovieImdbIDExcept(unexpectedMovieImdbID);

        return omdbHttpClient.findMovie(imdbID);
    }
}
