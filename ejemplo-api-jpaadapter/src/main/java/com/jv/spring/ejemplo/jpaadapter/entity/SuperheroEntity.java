package com.jv.spring.ejemplo.jpaadapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SUPERHERO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SuperheroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	/**
	 * Name of the superhero
	 */
	private String heroName;
	
	/**
	 * Real name of the superhero
	 */
	private String realName;
	
	/**
	 * Superpower of the superhero
	 */
	private String superpower;
}
