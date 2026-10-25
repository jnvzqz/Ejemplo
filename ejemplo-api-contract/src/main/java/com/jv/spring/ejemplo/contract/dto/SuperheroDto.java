package com.jv.spring.ejemplo.contract.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record SuperheroDto(
		@JsonProperty("Id")
		Long id,

		@NotNull(message = "El nombre del héroe no puede ser nulo")
		@JsonProperty("HeroName")
		String heroName,

		@JsonProperty("RealName")
		String realName,

		@JsonProperty("SuperPower")
		String superpower
) {}
