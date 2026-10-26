package com.jv.spring.ejemplo.jpaadapter.repository;

import com.jv.spring.ejemplo.jpaadapter.entity.SuperheroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroJpaRepository extends JpaRepository<SuperheroEntity, Long> {

    Page<SuperheroEntity> findByHeroNameContainingIgnoreCase(String heroName, Pageable pageable);

    long countByHeroNameContainingIgnoreCase(String heroName);
}
