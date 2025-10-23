package com.platzi_play.domain.exception;

public class MovieAlreadyExistException extends RuntimeException {
    public MovieAlreadyExistException(String movieTitle) {
        super("La pelicula con el titulo " + movieTitle + " ya existe.");
    }

}
