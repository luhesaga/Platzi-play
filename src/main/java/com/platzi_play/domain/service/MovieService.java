package com.platzi_play.domain.service;

import com.platzi_play.domain.dto.MovieDto;
import com.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi_play.domain.repository.MovieRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Tool("Busca todas las peliculas que existan dentro de la plataforma.")
    public List<MovieDto> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public MovieDto getMovieById(Long id) {
        return this.movieRepository.findById(id);
    }

    public MovieDto saveMovie(MovieDto movieDto) {
        return this.movieRepository.save(movieDto);
    }

    public MovieDto updateMovie(Long id, UpdateMovieDto updateMovieDto) {
        return this.movieRepository.update(id, updateMovieDto);
    }

    public void deleteMovie(Long id) {
        this.movieRepository.delete(id);
    }
}
