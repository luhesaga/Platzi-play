package com.platzi_play.domain.dto;

import com.platzi_play.domain.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovieDto(
    Long id,
    String title,
    Integer duration,
    Genre genre,
    LocalDate releaseDate,
    BigDecimal clasification,
    Boolean status
) {
}
