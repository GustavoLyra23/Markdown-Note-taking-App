package com.gustavolyra.markdown_api.controllers.handler;

import com.gustavolyra.markdown_api.exceptions.InvalidParamException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<String> handleException(InvalidParamException e, HttpServletRequest request) {
        return ResponseEntity.ok(e.getMessage());
    }
}
