package com.platzi_play.persistence;

import com.platzi_play.domain.dto.MovieDto;
import com.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi_play.domain.exception.MovieAlreadyExistException;
import com.platzi_play.domain.exception.MovieNotExistException;
import com.platzi_play.domain.repository.MovieRepository;
import com.platzi_play.persistence.crud.CrudMovieEntity;
import com.platzi_play.persistence.entity.MovieEntity;
import com.platzi_play.persistence.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieEntityRepository implements MovieRepository {

    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> findAll() {
        return this.movieMapper.toMovieDtoList(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDto findById(Long id) {
//        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);
//        return this.movieMapper.toMovieDto(movieEntity);
        return this.crudMovieEntity.findById(id)
                .map(this.movieMapper::toMovieDto)
                .orElse(null);
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        if (this.crudMovieEntity.findFirstByTitle(movieDto.title()) != null) {
            throw new MovieAlreadyExistException(movieDto.title());
        }

        MovieEntity movieEntity = this.movieMapper.toMovieEntity(movieDto);
        movieEntity.setStatus("D");

        return this.movieMapper.toMovieDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto update(Long id, UpdateMovieDto updateMovieDto) {
//        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
//                .orElse(null);
//
//        if (movieEntity == null) {
//            return null;
//        }
//
//        movieEntity.setTitle(updateMovieDto.title());
//        movieEntity.setReleaseDate(updateMovieDto.releaseDate());
//        movieEntity.setClasification(updateMovieDto.clasification());
//
//        return this.movieMapper.toMovieDto(this.crudMovieEntity.save(movieEntity));
        return this.crudMovieEntity.findById(id)
                .map(movieEntity -> {
                    this.movieMapper.updateMovieEntity(updateMovieDto, movieEntity);
                    return this.movieMapper.toMovieDto(this.crudMovieEntity.save(movieEntity));
                })
                .orElseThrow(() -> new MovieNotExistException(id));
    }

    @Override
    public void delete(Long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);

        if (movieEntity == null) {
            throw new MovieNotExistException(id);
        }

        crudMovieEntity.findById(id)
                .ifPresent(crudMovieEntity::delete);
    }
}
