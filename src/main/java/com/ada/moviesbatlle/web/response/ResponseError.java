package com.ada.moviesbatlle.web.response;

public class ResponseError {

    private final Error error;

    public ResponseError(String message) {
        error = new Error(message);
    }

    public Error getError() {
        return error;
    }
}
