package com.ada.moviesbatlle.web.controller;


import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.exceptions.*;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;
import com.ada.moviesbatlle.web.response.ErrorResponse;
import com.ada.moviesbatlle.web.response.QuestionResponse;
import com.ada.moviesbatlle.web.response.ResultResponse;
import com.ada.moviesbatlle.web.security.UserModel;
import com.ada.moviesbatlle.web.service.QuizService;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.web.data.QuestionData;
import com.ada.moviesbatlle.web.data.QuizData;
import com.ada.moviesbatlle.web.repository.QuizRepository;
import com.ada.moviesbatlle.web.response.QuizResponse;
import com.ada.moviesbatlle.web.data.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Value("${amount.rounds}")
    private int amountRounds;

    @Operation(summary = "Create and start a new Quiz for the current logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quiz created and started successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid authentication token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping("start")
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResponse startNewQuiz() {
        Quiz quiz = quizService.createQuiz(amountRounds);
        quizRepository.save(quiz, getLoggedUserID());

        QuizData quizData = QuizData.fromQuiz(quiz);

        QuizResponse quizResponse = new QuizResponse(quizData);

        return quizResponse;
    }

    @Operation(summary = "Stop a started Quiz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz stopped successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
            @ApiResponse(responseCode = "400", description = "Returned  quiz status is stopped or when informed ID is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid authentication token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping("{id}/stop")
    @ResponseStatus(HttpStatus.OK)
    public QuizResponse stopQuiz(@PathVariable String id) throws QuizNotStartedException, QuizNotFoundException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        quiz.stop();
        quizRepository.update(quiz);

        QuizData quizData = QuizData.fromQuiz(quiz);

        return new QuizResponse(quizData);
    }

    @Operation(summary = "Return Quiz current state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizResponse.class))),
            @ApiResponse(responseCode = "400", description = "Returned when when ID is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid authentication token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuizResponse getQuiz(@PathVariable String id) throws QuizNotFoundException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        QuizData quizData = QuizData.fromQuiz(quiz);

        return new QuizResponse(quizData);
    }

    @Operation(summary = "Return Quiz current Question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Returned when no more rounds exists, max wrong answers reached, " +
                    "quiz status is stopped or when informed ID is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid authentication token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})

    @GetMapping("{id}/question")
    @ResponseStatus(HttpStatus.OK)
    public QuestionResponse getQuestion(@PathVariable String id) throws MaxWrongAnswersException, QuizStoppedException, QuizNotFoundException, NoMoreRoundsException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        checkQuizAvailability(quiz);

        quiz.nextCurrentRound();
        quizRepository.update(quiz);

        if(quiz.getStatus() == QuizStatus.COMPLETED)
            throw new NoMoreRoundsException();

        QuestionData questionData = new QuestionData(quiz.getCurrentRound().getQuestion().getPrimaryMovie().getTitle(),
                quiz.getCurrentRound().getQuestion().getSecodaryMovie().getTitle());

        return new QuestionResponse(questionData);

    }

    @Operation(summary = "Answer Quiz current Question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question answered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Returned when round already answerded, no more rounds exists, max wrong answers reached, " +
                    "quiz status is stopped or when any informed parameter is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid authentication token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})

    @PostMapping("{id}/answer")
    @ResponseStatus(HttpStatus.OK)
    public ResultResponse answerCurrentQuestion(@PathVariable String id, @RequestParam(name = "a") Answer answer)
            throws RoundAlreadyAnswerdException, NoMoreRoundsException,
            MaxWrongAnswersException, QuizStoppedException, QuizNotFoundException, NotCurrentRoundException {

        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        checkQuizAvailability(quiz);

        Result result = quiz.answerCurrentRound(answer);

        quizRepository.update(quiz);

        ResultData resultData = new ResultData(result);
        return new ResultResponse(resultData);
    }

    private UUID getLoggedUserID() {
        return ((UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    private void checkQuizAvailability(Quiz quiz) throws MaxWrongAnswersException, NoMoreRoundsException, QuizStoppedException {
        if (quiz.getStatus() == QuizStatus.DEFEAT)
            throw new MaxWrongAnswersException();

        if (quiz.getStatus() == QuizStatus.COMPLETED)
            throw new NoMoreRoundsException();

        if (quiz.getStatus() == QuizStatus.STOPPED)
            throw new QuizStoppedException();
    }

}
