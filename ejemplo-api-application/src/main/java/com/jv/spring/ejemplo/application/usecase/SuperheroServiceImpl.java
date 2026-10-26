package com.jv.spring.ejemplo.application.usecase;

import com.jv.spring.ejemplo.application.mapper.SuperheroMapper;
import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.service.SuperheroService;
import com.jv.spring.ejemplo.core.exception.SuperheroNotFoundException;
import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.core.ports.outbound.SuperheroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository superheroRepository;
    private final SuperheroMapper superheroMapper;

    @Override
    public SuperheroDto createSuperhero(SuperheroDto dto) {
        Superhero superhero = superheroMapper.toModel(dto);
        return superheroMapper.toDto(superheroRepository.save(superhero));
    }

    @Override
    public SuperheroResponse findAllPaginated(int pageNo, int pageSize) {
        List<Superhero> heroes = superheroRepository.findAllPaginated(pageNo, pageSize);
        long totalElements = superheroRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = pageNo >= totalPages - 1;

        List<SuperheroDto> content = heroes.stream().map(superheroMapper::toDto).toList();

        log.info("{} superheroes found in the DB", totalElements);
        return new SuperheroResponse(content, pageNo, pageSize, totalElements, totalPages, isLast);
    }

    @Override
    public SuperheroDto findById(Long id) {
        Superhero superhero = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found"));
        return superheroMapper.toDto(superhero);
    }

    @Override
    public SuperheroResponse findByNamePaginated(String name, int pageNo, int pageSize) {
        List<Superhero> heroes = superheroRepository.findByNamePaginated(name, pageNo, pageSize);
        long totalElements = superheroRepository.countByName(name);
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isLast = pageNo >= totalPages - 1;

        List<SuperheroDto> content = heroes.stream().map(superheroMapper::toDto).toList();

        log.info("{} superheroes found matching '{}'", totalElements, name);
        return new SuperheroResponse(content, pageNo, pageSize, totalElements, totalPages, isLast);
    }

    @Override
    public SuperheroDto patchSuperhero(Long id, SuperheroDto patch) {
        Superhero existing = superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found"));

        Superhero updated = Superhero.builder()
                .id(existing.getId())
                .heroName(patch.heroName() != null ? patch.heroName() : existing.getHeroName())
                .realName(patch.realName() != null ? patch.realName() : existing.getRealName())
                .superpower(patch.superpower() != null ? patch.superpower() : existing.getSuperpower())
                .build();

        log.info("Superhero with id: {} updated", id);
        return superheroMapper.toDto(superheroRepository.save(updated));
    }

    @Override
    public void deleteSuperhero(Long id) {
        superheroRepository.findById(id)
                .orElseThrow(() -> new SuperheroNotFoundException("Superhero with id: " + id + " was not found"));
        superheroRepository.deleteById(id);
        log.info("Superhero with id: {} deleted", id);
    }
}
