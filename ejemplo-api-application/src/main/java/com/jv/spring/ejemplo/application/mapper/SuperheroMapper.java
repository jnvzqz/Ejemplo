package com.jv.spring.ejemplo.application.mapper;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.core.model.Superhero;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperheroMapper {

    SuperheroDto toDto(Superhero superhero);

    Superhero toModel(SuperheroDto dto);
}
