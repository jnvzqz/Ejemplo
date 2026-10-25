package com.jv.spring.ejemplo.rest.impl;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.service.SuperheroService;
import com.jv.spring.ejemplo.contract.exception.SuperheroNotFoundException;
import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.core.ports.outbound.SuperheroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository superheroRepository;

    @Override
    public SuperheroDto createSuperhero(SuperheroDto dto) {
        Superhero superhero = new Superhero(null, dto.heroName(), dto.realName(), dto.superpower());
        Superhero savedHero = superheroRepository.save(superhero);
        return toDto(savedHero);
    }

    @Override
    public SuperheroResponse obtenerTodosPaginado(int pageNo, int pageSize) {
        List<Superhero> heroes = superheroRepository.findAllPaginado(pageNo, pageSize);
        long totalElements = superheroRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = pageNo >= totalPages - 1;

        List<SuperheroDto> content = heroes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        log.info("{} superheroes were found in the DB", totalElements);
        return new SuperheroResponse(content, pageNo, pageSize, totalElements, totalPages, isLast);
    }

    @Override
    public SuperheroDto buscarPorId(Long id) {
        Superhero superhero = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
        return toDto(superhero);
    }

    @Override
    public SuperheroResponse buscarPorNombrePaginado(String nombre, int pageNo, int pageSize) {
        List<Superhero> heroes = superheroRepository.findByHeroNamePaginado(nombre, pageNo, pageSize);
        long totalElements = superheroRepository.countByHeroName(nombre);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = pageNo >= totalPages - 1;

        List<SuperheroDto> content = heroes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        log.info("{} superheroes were found with the matching string", totalElements);
        return new SuperheroResponse(content, pageNo, pageSize, totalElements, totalPages, isLast);
    }

    @Override
    public SuperheroDto patchSuperhero(Long id, SuperheroDto patchedSuperhero) {
        Superhero existingHero = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));

        Superhero updatedHero = new Superhero(
                existingHero.getId(),
                patchedSuperhero.heroName() != null ? patchedSuperhero.heroName() : existingHero.getHeroName(),
                patchedSuperhero.realName() != null ? patchedSuperhero.realName() : existingHero.getRealName(),
                patchedSuperhero.superpower() != null ? patchedSuperhero.superpower() : existingHero.getSuperpower()
        );

        Superhero savedHero = superheroRepository.save(updatedHero);
        log.info("Superhero with id: {} was updated in the DB", id);
        return toDto(savedHero);
    }

    @Override
    public void deleteSuperhero(Long id) {
        superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found in the DB"));
        superheroRepository.deleteById(id);
        log.info("Superhero with id: {} was deleted from the DB", id);
    }

    // Mapeo manual local: Rápido, seguro y libre de problemas de configuración en directo
    private SuperheroDto toDto(Superhero model) {
        return new SuperheroDto(Long.parseLong(model.getId()), model.getHeroName(), model.getRealName(), model.getSuperpower());
    }
}
