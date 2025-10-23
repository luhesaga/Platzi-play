package com.platzi_play.persistence.mapper;

import com.platzi_play.domain.Genre;
import org.mapstruct.Named;

public class GenreMapper {

    @Named( "toGenre")
    public static Genre toGenre(String genre){
        if (genre == null) {
            return null;
        }

        return switch (genre.toUpperCase()) {
            case "ACCION" -> Genre.ACTION;
            case "COMEDIA" -> Genre.COMEDY;
            case "DRAMA" -> Genre.DRAMA;
            case "FANTASIA" -> Genre.FANTASY;
            case "TERROR" -> Genre.HORROR;
            case "ROMANCE" -> Genre.ROMANCE;
            case "THRILLER" -> Genre.THRILLER;
            case "WESTERN" -> Genre.WESTERN;
            case "ANIMADA" -> Genre.ANIMATION;
            case "CIENCIA_FICCION" -> Genre.SCI_FI;
            default -> null;
        };
    }

    @Named( "toString")
    public static String toString(Genre genre){
        if (genre == null) {
            return null;
        }

        return switch (genre) {
            case ACTION -> "ACCION";
            case COMEDY -> "COMEDIA";
            case DRAMA -> "DRAMA";
            case FANTASY -> "FANTASIA";
            case HORROR -> "TERROR";
            case ROMANCE -> "ROMANCE";
            case THRILLER -> "THRILLER";
            case WESTERN -> "WESTERN";
            case ANIMATION -> "ANIMADA";
            case SCI_FI -> "CIENCIA_FICCION";
        };
    }
}
