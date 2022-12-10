package com.ada.moviesbatlle.web.controller;


import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.exceptions.*;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;
import com.ada.moviesbatlle.web.security.UserModel;
import com.ada.moviesbatlle.web.service.QuizService;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.web.dto.QuestionDto;
import com.ada.moviesbatlle.web.dto.QuizDto;
import com.ada.moviesbatlle.web.repository.QuizRepository;
import com.ada.moviesbatlle.web.response.Response;
import com.ada.moviesbatlle.web.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("quiz/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<QuizDto> startNewQuiz() {
        Quiz quiz = quizService.createQuiz();
        quizRepository.save(quiz, getLoggedUserID());

        QuizDto quizDto = QuizDto.fromQuiz(quiz);

        Response<QuizDto> response = new Response<>(quizDto);

        return response;
    }

    @PostMapping("quiz/{id}/stop")
    @ResponseStatus(HttpStatus.OK)
    public Response<QuizDto> stopQuiz(@PathVariable String id) throws QuizNotStartedException, QuizNotFoundException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        quiz.stop();
        quizRepository.update(quiz);

        QuizDto quizDto = QuizDto.fromQuiz(quiz);

        Response<QuizDto> response = new Response<>(quizDto);

        return response;
    }

    @GetMapping("quiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<QuizDto> getQuiz(@PathVariable String id) throws QuizNotFoundException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());

        QuizDto quizDto = QuizDto.fromQuiz(quiz);

        Response<QuizDto> response = new Response<>(quizDto);

        return response;
    }

    @GetMapping("quiz/{id}/question")
    @ResponseStatus(HttpStatus.OK)
    public Response<QuestionDto> getQuestion(@PathVariable String id) throws NoMoreRoundsException, QuizNotStartedException,
            MaxWrongAnswersException, QuizStoppedException, QuizNotFoundException {
        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());

        try {
            QuestionDto questionDto = new QuestionDto(quiz.nextCurrentRound().getQuestion().getPrimaryMovie().getTitle(),
                    quiz.nextCurrentRound().getQuestion().getSecodaryMovie().getTitle());

            quizRepository.update(quiz);
            return new Response<>(questionDto);
        } catch (NoMoreRoundsException ex) {
            quizRepository.update(quiz);
            throw ex;
        }
    }

    @PostMapping("quiz/{id}/answer")
    @ResponseStatus(HttpStatus.OK)
    public Response<ResultDto> answerCurrentQuestion(@PathVariable String id, @RequestParam(name = "a") Answer answer)
            throws RoundAlreadyAnswerdException, NoMoreRoundsException,
            MaxWrongAnswersException, QuizStoppedException, QuizNotFoundException {

        Quiz quiz = quizRepository.selectQuiz(UUID.fromString(id), getLoggedUserID());
        Result result = quiz.answerCurrentRound(answer);

        quizRepository.update(quiz);

        ResultDto resultDto = new ResultDto(result);
        Response<ResultDto> response = new Response<>(resultDto);

        return response;
    }

    private UUID getLoggedUserID() {
        return ((UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
