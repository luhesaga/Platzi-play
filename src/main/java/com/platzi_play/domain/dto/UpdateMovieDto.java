package com.platzi_play.domain.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateMovieDto(
    @NotBlank(message = "The title is required.")
    String title,

    @PastOrPresent(message = "The release date must be a past or present date.")
    LocalDate releaseDate,

    @Min(value = 0, message = "The duration can´t be lower than 0.")
    @Max(value = 5, message = "The duration must be maximum 5")
    BigDecimal clasification
) {
}
