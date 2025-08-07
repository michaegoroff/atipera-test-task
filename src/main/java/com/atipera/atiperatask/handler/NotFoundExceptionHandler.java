package com.atipera.atiperatask.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(value = NotFound.class)
    public ResponseEntity<Object> handleNotFoundException(NotFound notFound) {
        UserNotFoundException userNotFoundException = new UserNotFoundException(notFound.getStatusCode().toString(),
                notFound.getLocalizedMessage());
        return new ResponseEntity<>(userNotFoundException, HttpStatus.NOT_FOUND);
    }

    private record UserNotFoundException(String status, String message) {

    }
}
