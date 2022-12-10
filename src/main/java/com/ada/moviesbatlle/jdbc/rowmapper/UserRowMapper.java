package com.ada.moviesbatlle.jdbc.rowmapper;

import com.ada.moviesbatlle.web.security.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<UserModel> {
    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserModel userModel = new UserModel();
        userModel.setUserId(rs.getObject("id", UUID.class));
        userModel.setUsername(rs.getString("username"));
        userModel.setPassword(rs.getString("password"));

        return userModel;
    }
}
