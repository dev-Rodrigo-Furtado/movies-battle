package com.ada.moviesbatlle.web.service.impl;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.fixtures.Fixtures;
import com.ada.moviesbatlle.omdb.client.OmdbHttpClient;
import com.ada.moviesbatlle.web.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ada.moviesbatlle.fixtures.Fixtures.buildMovie;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {

    @InjectMocks
    public MovieServiceImpl movieService;

    @Mock
    public OmdbHttpClient omdbHttpClient;

    @Mock
    public MovieRepository movieRepository;

    @Test
    public void getRandomMovie_shouldReturnMovie() {
        Movie expectedMovie = buildMovie("tt0167261", "The Lord of the Rings: The Two Towers");

        when(movieRepository.findRandomMovieImdbID())
                .thenReturn("tt0167261");

        when((omdbHttpClient.findMovie("tt0167261")))
                .thenReturn(expectedMovie);

        Movie movie = movieService.getRandomMovie();

        assertEquals(expectedMovie, movie);
    }

    @Test
    public void getRandomMovieExcept_shouldReturnMovie() {
        Movie expectedMovie = buildMovie("tt0167261", "The Lord of the Rings: The Two Towers");

        when(movieRepository.findRandomMovieImdbIDExcept("tt1745960"))
                .thenReturn("tt0167261");

        when((omdbHttpClient.findMovie("tt0167261")))
                .thenReturn(expectedMovie);

        Movie movie = movieService.getRandomMovieExcept("tt1745960");

        assertEquals(expectedMovie, movie);
    }


}