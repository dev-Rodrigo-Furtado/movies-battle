package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.domain.exceptions.*;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;
import com.ada.moviesbatlle.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception ex, HttpServletRequest req) {

        return ResponseEntity
                .internalServerError().build();

    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleParametersException(Exception ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid parameters format");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }


    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleQuizNotFoundException(QuizNotFoundException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("Quiz not found, please enter a valid quiz id");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(QuizNotStartedException.class)
    public ResponseEntity<ErrorResponse> handleQuizNotStartedException(QuizNotStartedException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("Quiz does not have STARTED status");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(RoundAlreadyAnswerdException.class)
    public ResponseEntity<ErrorResponse> roundAlreadyAnswerdException(RoundAlreadyAnswerdException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("You've already answerded this round, please " +
                "go to the next.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NoMoreRoundsException.class)
    public ResponseEntity<ErrorResponse> noMoreRoundsException(NoMoreRoundsException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("You've already completed this Quiz, please " +
                "start a new one to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MaxWrongAnswersException.class)
    public ResponseEntity<ErrorResponse> maxWrongAnswersException(MaxWrongAnswersException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("You have reached the maximum allowed errors, please start a new" +
                " Quiz to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(QuizAlreadyStartedException.class)
    public ResponseEntity<ErrorResponse> quizAlreadyStartedException(QuizAlreadyStartedException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("You have already started a Quiz, please answer it all " +
                "or stop it.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(QuizStoppedException.class)
    public ResponseEntity<ErrorResponse> quizStoppedException(QuizStoppedException ex, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse("You have stopped this Quiz, please start a new" +
                " Quiz to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
