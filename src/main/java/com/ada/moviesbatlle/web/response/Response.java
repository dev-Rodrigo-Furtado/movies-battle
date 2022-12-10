package com.ada.moviesbatlle.web.response;

public class Response<T> {
    private T data;

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

