package com.ada.moviesbatlle.jdbc.rowmapper;

import com.ada.moviesbatlle.domain.models.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        String imdbID = rs.getString("imdb_id");
        String title = rs.getString("title");
        double imdbRating = rs.getDouble("imdb_rating");
        int imdbVotes = rs.getInt("imdb_votes");

        return new Movie(imdbID, title, imdbRating, imdbVotes);
    }
}
