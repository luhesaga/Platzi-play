package com.platzi_play.web.exception;

import com.platzi_play.domain.exception.MovieAlreadyExistException;
import com.platzi_play.domain.exception.MovieNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MovieAlreadyExistException.class)
    public ResponseEntity<Error> handleException(MovieAlreadyExistException ex) {
        Error error = new Error("movie_already_exist", ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MovieNotExistException.class)
    public ResponseEntity<Error> handleException(MovieNotExistException ex) {
        Error error = new Error("movie_does_not_exist", ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errors);
    }

    // Excepción general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        Error error = new Error("unknown_error", ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
