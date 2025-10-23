package com.platzi_play.domain.exception;

public class MovieNotExistException extends RuntimeException {
    public MovieNotExistException(Long id) {
        super("La pelicula con el id " + id + " no existe.");
    }
}
