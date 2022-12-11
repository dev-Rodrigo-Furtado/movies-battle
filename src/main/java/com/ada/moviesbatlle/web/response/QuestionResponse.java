package com.ada.moviesbatlle.web.response;

import com.ada.moviesbatlle.web.data.QuestionData;

public class QuestionResponse {
    private QuestionData data;

    public QuestionResponse(QuestionData data) {
        this.data = data;
    }

    public QuestionData getData() {
        return data;
    }
}

