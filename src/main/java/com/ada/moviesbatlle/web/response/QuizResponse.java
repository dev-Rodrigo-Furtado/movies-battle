package com.ada.moviesbatlle.web.response;

import com.ada.moviesbatlle.web.data.QuizData;

public class QuizResponse {
    private QuizData data;

    public QuizResponse(QuizData data) {
        this.data = data;
    }

    public QuizData getData() {
        return data;
    }

    public void setData(QuizData data) {
        this.data = data;
    }
}

