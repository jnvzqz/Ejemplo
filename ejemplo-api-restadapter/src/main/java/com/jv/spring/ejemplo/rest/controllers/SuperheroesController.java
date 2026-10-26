package com.jv.spring.ejemplo.rest.controllers;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.service.SuperheroService;
import jakarta.validation.Valid;
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

    private final SuperheroService superheroService;

    @PostMapping
    public ResponseEntity<SuperheroDto> addSuperhero(@RequestBody @Valid SuperheroDto superheroDto) {
        SuperheroDto newSuperhero = superheroService.createSuperhero(superheroDto);
        log.info("New superhero created with id: {}", newSuperhero.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(newSuperhero);
    }

    @GetMapping
    public ResponseEntity<SuperheroResponse> getAllSuperheroes(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(superheroService.findAllPaginated(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperheroDto> getSuperheroById(@PathVariable Long id) {
        SuperheroDto superhero = superheroService.findById(id);
        log.info("Superhero with id: {} found", superhero.id());
        return ResponseEntity.ok(superhero);
    }

    @GetMapping("/search")
    public ResponseEntity<SuperheroResponse> getSuperheroByName(
            @RequestParam String name,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(superheroService.findByNamePaginated(name, pageNo, pageSize));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuperheroDto> patchSuperhero(@PathVariable Long id, @RequestBody @Valid SuperheroDto superheroDto) {
        SuperheroDto patched = superheroService.patchSuperhero(id, superheroDto);
        log.info("Superhero with id: {} updated", patched.id());
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {
        superheroService.deleteSuperhero(id);
        log.info("Superhero with id: {} deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
