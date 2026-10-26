package com.jv.spring.ejemplo.contract.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record SuperheroDto(
		@JsonProperty("Id")
		Long id,

		@NotNull(message = "Hero name must not be null")
		@JsonProperty("HeroName")
		String heroName,

		@JsonProperty("RealName")
		String realName,

		@JsonProperty("SuperPower")
		String superpower
) {}
