package com.ada.moviesbatlle.web.security;

import java.util.Optional;

public interface UserRepository {

    Optional<UserModel> findByUsername(String username);
}
