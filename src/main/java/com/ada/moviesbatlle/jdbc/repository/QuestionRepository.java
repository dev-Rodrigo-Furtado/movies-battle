package com.ada.moviesbatlle.jdbc.repository;

import com.ada.moviesbatlle.domain.models.Question;

import java.util.UUID;

public interface QuestionRepository {
    void save(Question question, UUID roundID);
}
