package com.jv.spring.ejemplo.jpaadapter.adapter;

import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.core.ports.outbound.SuperheroRepository;
import com.jv.spring.ejemplo.jpaadapter.mapper.SuperheroEntityMapper;
import com.jv.spring.ejemplo.jpaadapter.repository.SuperheroJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SuperheroJpaAdapter implements SuperheroRepository {

    private final SuperheroJpaRepository jpaRepository;
    private final SuperheroEntityMapper mapper;

    @Override
    public Superhero save(Superhero superhero) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(superhero)));
    }

    @Override
    public Optional<Superhero> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Superhero> findAllPaginado(int pageNo, int pageSize) {
        return jpaRepository.findAll(PageRequest.of(pageNo, pageSize))
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Superhero> findByHeroNamePaginado(String heroName, int pageNo, int pageSize) {
        return jpaRepository.findByHeroNameContainingIgnoreCase(heroName, PageRequest.of(pageNo, pageSize))
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long countAll() {
        return jpaRepository.count();
    }

    @Override
    public long countByHeroName(String heroName) {
        return jpaRepository.countByHeroNameContainingIgnoreCase(heroName);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
