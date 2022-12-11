package com.ada.moviesbatlle.omdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OmdbWebClientConfig {

    @Bean
    public WebClient omdbWebClient() {
        WebClient webClient = WebClient.create("http://www.omdbapi.com");
        return webClient;
    }
}
