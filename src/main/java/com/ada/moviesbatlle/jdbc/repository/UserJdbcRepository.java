package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.jdbc.rowmapper.UserRowMapper;
import com.ada.moviesbatlle.web.security.UserModel;
import com.ada.moviesbatlle.web.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJdbcRepository implements UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${find.user.by.username.query}")
    private String findUserByUsernameQuery;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("username", username);

       try {
           return Optional.of(namedParameterJdbcTemplate.queryForObject(findUserByUsernameQuery, parameterSource,
                   new UserRowMapper()));
       } catch (Exception e) {
           return Optional.empty();
       }
    }
}
