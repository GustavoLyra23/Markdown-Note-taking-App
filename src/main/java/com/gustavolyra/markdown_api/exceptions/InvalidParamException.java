package com.gustavolyra.markdown_api.exceptions;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String message) {
        super(message);
    }
}
