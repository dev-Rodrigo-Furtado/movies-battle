package com.ada.moviesbatlle.omdb.client;

import com.ada.moviesbatlle.domain.models.Movie;

public interface OmdbHttpClient {

    Movie findMovie(String imdbID);
}
