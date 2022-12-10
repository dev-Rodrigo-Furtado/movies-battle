package com.ada.moviesbatlle.web.controller;

import com.ada.moviesbatlle.domain.exceptions.*;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;
import com.ada.moviesbatlle.web.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest req) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ResponseError> handleQuizNotFoundException(QuizNotFoundException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("Quiz not found, please enter a valid quiz id");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(QuizNotStartedException.class)
    public ResponseEntity<ResponseError> handleQuizNotStartedException(QuizNotStartedException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("Quiz does not have STARTED status");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(RoundAlreadyAnswerdException.class)
    public ResponseEntity<ResponseError> roundAlreadyAnswerdException(RoundAlreadyAnswerdException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("You've already answerded this round, please " +
                "go to the next.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(NoMoreRoundsException.class)
    public ResponseEntity<ResponseError> noMoreRoundsException(NoMoreRoundsException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("You've already completed this Quiz, please " +
                "start a new one to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(MaxWrongAnswersException.class)
    public ResponseEntity<ResponseError> maxWrongAnswersException(MaxWrongAnswersException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("You have reached the maximum allowed errors, please start a new" +
                " Quiz to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(QuizAlreadyStartedException.class)
    public ResponseEntity<ResponseError> quizAlreadyStartedException(QuizAlreadyStartedException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("You have already started a Quiz, please answer it all " +
                "or stop it.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }

    @ExceptionHandler(QuizStoppedException.class)
    public ResponseEntity<ResponseError> quizStoppedException(QuizStoppedException ex, HttpServletRequest req) {
        ResponseError responseError = new ResponseError("You have stopped this Quiz, please start a new" +
                " Quiz to play more.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseError);
    }
}
