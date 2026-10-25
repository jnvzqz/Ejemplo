package com.jv.spring.ejemplo.rest.controllers;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.service.SuperheroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/superheroes")
public class SuperheroesController {

	private final SuperheroService superheroesService;

	@PostMapping
	public ResponseEntity<SuperheroDto> addSuperhero(@RequestBody SuperheroDto superheroDto) {
		SuperheroDto newSuperhero = superheroesService.createSuperhero(superheroDto);
		log.info("New superhero created with id: {}", newSuperhero.id());
		return ResponseEntity.status(HttpStatus.CREATED).body(newSuperhero);
	}

	@GetMapping
	public ResponseEntity<SuperheroResponse> getAllSuperheroes(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		SuperheroResponse response = superheroesService.obtenerTodosPaginado(pageNo, pageSize);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SuperheroDto> getSuperheroById(@PathVariable Long id) {
		SuperheroDto superhero = superheroesService.buscarPorId(id);
		log.info("Superhero with id: {} was found", superhero.id());
		return ResponseEntity.ok(superhero);
	}

	@GetMapping("/search")
	public ResponseEntity<SuperheroResponse> getSuperheroByName(
			@RequestParam String name,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		SuperheroResponse response = superheroesService.buscarPorNombrePaginado(name, pageNo, pageSize);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<SuperheroDto> patchSuperhero(@PathVariable Long id, @RequestBody SuperheroDto superheroDto) {
		SuperheroDto patchedSuperhero = superheroesService.patchSuperhero(id, superheroDto);
		log.info("Superhero with id: {} was updated in the DB", patchedSuperhero.id());
		return ResponseEntity.ok(patchedSuperhero);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {
		superheroesService.deleteSuperhero(id);
		log.info("Superhero with id: {} was deleted from the DB", id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
