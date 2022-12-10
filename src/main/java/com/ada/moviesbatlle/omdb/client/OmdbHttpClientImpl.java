package com.ada.moviesbatlle.omdb.client;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.omdb.response.OmdbMovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class OmdbHttpClientImpl implements OmdbHttpClient{

    @Autowired
    private WebClient omdbWebClient;

    @Value("${omdb.api.api-key}")
    private String omdbApiKey;

    public Movie findMovie(String imdbID) {

      WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = omdbWebClient.method(HttpMethod.GET);

      WebClient.RequestBodySpec bodySpec = uriSpec.uri(
                uriBuilder -> uriBuilder
                        .queryParam("apikey", omdbApiKey)
                        .queryParam("i", imdbID)
                        .build());

      Mono<OmdbMovieResponse> response = bodySpec.retrieve()
              .bodyToMono(OmdbMovieResponse.class);

      OmdbMovieResponse omdbMovieResponse = response.block();
      return omdbMovieResponse.toModel();
    }

}
