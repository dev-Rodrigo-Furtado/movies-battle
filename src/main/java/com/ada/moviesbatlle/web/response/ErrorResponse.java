package com.ada.moviesbatlle.web.response;

public class ErrorResponse {

    private final Error error;

    public ErrorResponse(String message) {
        error = new Error(message);
    }

    public Error getError() {
        return error;
    }
}
