package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Movie;
import com.ada.moviesbatlle.domain.models.Question;
import com.ada.moviesbatlle.domain.models.Quiz;
import com.ada.moviesbatlle.domain.models.Round;
import com.ada.moviesbatlle.omdb.client.OmdbHttpClientImpl;
import com.ada.moviesbatlle.jdbc.entity.QuizEntity;
import com.ada.moviesbatlle.jdbc.rowmapper.QuizRowMapper;
import com.ada.moviesbatlle.web.exceptions.QuizNotFoundException;
import com.ada.moviesbatlle.web.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class QuizJdbcRepository implements QuizRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RoundJdbcRepository roundJdbcRepository;

    @Autowired
    private OmdbHttpClientImpl omdbHttpClient;

    @Value("${insert.quiz.query}")
    private String insertQuizQuery;

    @Value("${find.quiz.by.id}")
    private String findQuizByIDQuery;

    @Value("${update.quiz.by.id}")
    private String updateQuizByIDQuery;

    @Override
    public void save(Quiz quiz, UUID loggedUserID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", quiz.getID())
                .addValue("user_id", loggedUserID)
                .addValue("score", quiz.getScore())
                .addValue("total_correct_answers", quiz.getTotalCorrectAnswers())
                .addValue("total_wrong_answers", quiz.getTotalWrongAnswers())
                .addValue("status", quiz.getStatus().toString());

        namedParameterJdbcTemplate.update(insertQuizQuery, parameterSource);
        roundJdbcRepository.saveList(quiz.getRounds(), quiz.getID());
    }

    @Override
    public void update(Quiz quiz) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", quiz.getID())
                .addValue("score", quiz.getScore())
                .addValue("total_correct_answers", quiz.getTotalCorrectAnswers())
                .addValue("total_wrong_answers", quiz.getTotalWrongAnswers())
                .addValue("status", quiz.getStatus().toString());

        namedParameterJdbcTemplate.update(updateQuizByIDQuery, parameterSource);
        roundJdbcRepository.updateList(quiz.getRounds());
    }

    @Override
    public Quiz selectQuiz(UUID quizID, UUID loggedUserID) throws QuizNotFoundException {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", quizID)
                .addValue("user_id", loggedUserID);

        List<QuizEntity> quizEntityList = namedParameterJdbcTemplate.query(findQuizByIDQuery, parameterSource,
                new QuizRowMapper());

        List<Round> rounds = quizEntityList.stream().map(quizEntity -> {
            Movie primaryMovie = omdbHttpClient.findMovie(quizEntity.getQuestionPrimaryMovieID());
            Movie secondaryMovie = omdbHttpClient.findMovie(quizEntity.getQuestionSecondaryMovieID());
            Question question = new Question(quizEntity.getQuestionUUID(), primaryMovie, secondaryMovie);

            return new Round(quizEntity.getRoundUUID(), question, quizEntity.getRoundStatus(), quizEntity.getRoundResult());
        }).collect(Collectors.toList());

        try {
            return new Quiz(quizEntityList.get(0).getQuizUUID(), rounds, quizEntityList.get(0).getQuizStatus(),
                    quizEntityList.get(0).getQuizScore(), quizEntityList.get(0).getQuizTotalCorrectAnswers(),
                    quizEntityList.get(0).getQuizTotalWrongAnswers());
        } catch (Exception ex) {
            throw new QuizNotFoundException();
        }

    }

}
