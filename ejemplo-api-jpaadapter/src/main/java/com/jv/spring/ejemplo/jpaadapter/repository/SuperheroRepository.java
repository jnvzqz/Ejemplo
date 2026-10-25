package com.jv.spring.ejemplo.jpaadapter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;	
import com.jv.spring.ejemplo.jpaadapter.entity.SuperheroEntity;

@Repository
public interface SuperheroRepository extends JpaRepository<SuperheroEntity, Long> {
	
	/**
	 * Finds a all the superheroes.
	 * 
	 * */
	List<SuperheroEntity> findAll();
	
	/**
	 * Finds a superhero by the provided id.
	 * 
	 * @param id The provided id.
	 * */
	SuperheroEntity findById(long id);
	
	/**
	 * Finds all superheroes whose name contains a specific string.
	 * 
	 * @param heroName The specified string.
	 * */
	List<SuperheroEntity> findByHeroNameContainingIgnoreCase(String heroName);	
	
}
