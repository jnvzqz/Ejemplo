package com.jv.spring.ejemplo.jpaadapter.mapper;

import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.jpaadapter.entity.SuperheroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SuperheroEntityMapper {

    Superhero toDomain(SuperheroEntity entity);

    SuperheroEntity toEntity(Superhero domain);

}
