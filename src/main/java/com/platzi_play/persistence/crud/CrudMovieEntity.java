package com.platzi_play.persistence.crud;

import com.platzi_play.persistence.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudMovieEntity extends CrudRepository<MovieEntity, Long> {
    MovieEntity findFirstByTitle(String title);
}
