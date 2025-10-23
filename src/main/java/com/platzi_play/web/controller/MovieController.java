package com.platzi_play.web.controller;

import com.platzi_play.domain.dto.MovieDto;
import com.platzi_play.domain.dto.SuggestRequestDto;
import com.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi_play.domain.service.MovieService;
import com.platzi_play.domain.service.PlatziPlayAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Endpoints to management of movies in PlatziPlay.")
public class MovieController {

    private final MovieService movieService;
    private final PlatziPlayAiService platziPlayAiService;

    public MovieController(MovieService movieService, PlatziPlayAiService platziPlayAiService) {
        this.movieService = movieService;
        this.platziPlayAiService = platziPlayAiService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(this.movieService.getAllMovies());
    }

//    @GetMapping
//    public List<MovieDto> getAllMovies() {
//        return this.movieService.getAllMovies();
//    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a movie by its ID.",
            description = "Returns a movie with the given ID if it exists.",
            responses = {
                  @ApiResponse(responseCode = "200", description = "Movie found."),
                  @ApiResponse(responseCode = "404", description = "Movie not found.", content = @Content)
            }
    )
    public ResponseEntity<MovieDto> getMovieById(
                @Parameter(description = "The ID of the movie to retrieve.", example = "1")
                @PathVariable Long id) {

        MovieDto movieDto = this.movieService.getMovieById(id);

        if (movieDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movieDto);
    }

//    @GetMapping("/{id}")
//    public MovieDto getMovieById(@PathVariable Long id) {
//        return this.movieService.getMovieById(id);
//    }
    @PostMapping("/suggestions")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestRequestDto suggestRequestDto) {
        return ResponseEntity
                .ok(this.platziPlayAiService.generateMoviesuggestions(suggestRequestDto.userPreferences()));
    }

    @PostMapping
    public ResponseEntity<MovieDto> saveMovie(@RequestBody MovieDto movieDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.movieService.saveMovie(movieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id ,@RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(this.movieService.updateMovie(id, updateMovieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        this.movieService.deleteMovie(id);

        return ResponseEntity.ok("Movie with ID " + id + " deleted successfully.");
    }
}
