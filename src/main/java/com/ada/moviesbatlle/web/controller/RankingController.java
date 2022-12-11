package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.domain.models.Ranking;
import com.ada.moviesbatlle.jdbc.repository.RankingRepository;
import com.ada.moviesbatlle.web.data.RankingData;
import com.ada.moviesbatlle.web.response.RankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RankingController {

    @Autowired
    private RankingRepository rankingRepository;

    @Operation(summary = "Return Ranking current state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ranking retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RankingResponse.class)))})

    @GetMapping("ranking")
    @ResponseStatus(HttpStatus.CREATED)
    public RankingResponse getRanking() {
        Ranking ranking = rankingRepository.getRanking();

        RankingData rankingData = RankingData.fromRanking(ranking);
        return new RankingResponse(rankingData);
    }

}
