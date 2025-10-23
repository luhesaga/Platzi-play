package com.platzi_play.persistence.mapper;

import com.platzi_play.domain.dto.MovieDto;
import com.platzi_play.domain.dto.UpdateMovieDto;
import com.platzi_play.persistence.entity.MovieEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class, StatusMapper.class})
public interface MovieMapper {

    @Mapping(source = "genre", target = "genre", qualifiedByName = "toGenre")
    @Mapping(source = "status", target = "status", qualifiedByName = "toBoolean")
    MovieDto toMovieDto(MovieEntity movieEntity);
    List<MovieDto> toMovieDtoList(Iterable<MovieEntity> movieEntityList);

    @InheritInverseConfiguration
    @Mapping(source = "genre", target = "genre", qualifiedByName = "toString")
    @Mapping(source = "status", target = "status", qualifiedByName = "toString")
    MovieEntity toMovieEntity(MovieDto movieDto);

    void updateMovieEntity(UpdateMovieDto updateMovieDto, @MappingTarget MovieEntity movieEntity);
}
