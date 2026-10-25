package com.jv.spring.ejemplo.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Superhero {
	
	 public static class SuperheroEntityBuilder {}

	private String id;
	private String heroName;
	private String realName;
	private String superpower;
}
