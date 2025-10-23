package com.platzi_play.persistence.mapper;

import org.mapstruct.Named;

public class StatusMapper {

    @Named("toBoolean")
    public static Boolean toBoolean(String status){

        if (status == null) {
            return false;
        }

        return switch (status) {
            case "D" -> true;
            case "N" -> false;
            default -> null;
        };
    }

    @Named("toString")
    public static String toString(Boolean status) {

        if ((status == null)) {
            return null;
        } else {
            if (status) return "D";
            return "N";
        }
    }
}
