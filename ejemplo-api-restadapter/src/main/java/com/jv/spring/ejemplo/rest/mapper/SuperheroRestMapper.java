package com.jv.spring.ejemplo.rest.mapper;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.core.model.Superhero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SuperheroRestMapper {

	// Transforma el modelo puro del core en un DTO para enviarlo por HTTP
	SuperheroDto toDto(Superhero model);

	// Transforma el DTO recibido en la petición REST en un modelo puro de negocio
	Superhero toModel(SuperheroDto dto);
}
