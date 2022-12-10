package com.ada.moviesbatlle.jdbc.rowmapper;

import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.jdbc.entity.QuizEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class QuizRowMapper implements RowMapper<QuizEntity> {
    @Override
    public QuizEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizEntity quizEntity = new QuizEntity();

        quizEntity.setQuizUUID(UUID.fromString(rs.getString("quizzes.id")));
        quizEntity.setQuizScore(rs.getInt("quizzes.score"));
        quizEntity.setQuizTotalWrongAnswers(rs.getInt("quizzes.total_wrong_answers"));
        quizEntity.setQuizStatus(QuizStatus.valueOf(rs.getString("quizzes.status")));

        quizEntity.setRoundUUID(UUID.fromString(rs.getString("rounds.id")));
        quizEntity.setRoundResult(Result.valueOf(rs.getString("rounds.result")));
        quizEntity.setRoundStatus(RoundStatus.valueOf(rs.getString("rounds.status")));

        quizEntity.setQuestionUUID(UUID.fromString(rs.getString("questions.id")));
        quizEntity.setQuestionPrimaryMovieID(rs.getString("questions.primary_movie_imdb_id"));
        quizEntity.setQuestionSecondaryMovieID(rs.getString("questions.secondary_movie_imdb_id"));

        return quizEntity;
    }
}
