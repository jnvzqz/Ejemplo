package com.jv.spring.ejemplo.jpaadapter.adapter;

import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.jpaadapter.entity.SuperheroEntity;
import com.jv.spring.ejemplo.jpaadapter.mapper.SuperheroEntityMapper;
import com.jv.spring.ejemplo.jpaadapter.repository.SuperheroJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroJpaAdapterTest {

    @Mock
    private SuperheroJpaRepository jpaRepository;

    @Mock
    private SuperheroEntityMapper mapper;

    @InjectMocks
    private SuperheroJpaAdapter adapter;

    @Test
    void testSave() {
        //Given
        Superhero domain = new Superhero(null, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroEntity entity = new SuperheroEntity(null, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroEntity savedEntity = new SuperheroEntity(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero savedDomain = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(savedDomain);

        //When
        Superhero result = adapter.save(domain);

        //Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Spider-Man", result.getHeroName());
    }

    @Test
    void testFindById_found() {
        //Given
        SuperheroEntity entity = new SuperheroEntity(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero domain = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        //When
        Optional<Superhero> result = adapter.findById(1L);

        //Then
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Spider-Man", result.get().getHeroName());
    }

    @Test
    void testFindById_notFound() {
        //Given
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        //When
        Optional<Superhero> result = adapter.findById(99L);

        //Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllPaginated() {
        //Given
        SuperheroEntity entity = new SuperheroEntity(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero domain = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(jpaRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDomain(entity)).thenReturn(domain);

        //When
        List<Superhero> result = adapter.findAllPaginated(0, 10);

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spider-Man", result.get(0).getHeroName());
    }

    @Test
    void testFindAllPaginated_emptyList() {
        //Given
        when(jpaRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        //When
        List<Superhero> result = adapter.findAllPaginated(0, 10);

        //Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNamePaginated() {
        //Given
        SuperheroEntity entity = new SuperheroEntity(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero domain = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(jpaRepository.findByHeroNameContainingIgnoreCase(eq("Spider"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDomain(entity)).thenReturn(domain);

        //When
        List<Superhero> result = adapter.findByNamePaginated("Spider", 0, 10);

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spider-Man", result.get(0).getHeroName());
    }

    @Test
    void testFindByNamePaginated_noResults() {
        //Given
        when(jpaRepository.findByHeroNameContainingIgnoreCase(eq("Xyzzy"), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        //When
        List<Superhero> result = adapter.findByNamePaginated("Xyzzy", 0, 10);

        //Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCountAll() {
        //Given
        when(jpaRepository.count()).thenReturn(5L);

        //When
        long result = adapter.countAll();

        //Then
        assertEquals(5L, result);
    }

    @Test
    void testCountByName() {
        //Given
        when(jpaRepository.countByHeroNameContainingIgnoreCase("Spider")).thenReturn(2L);

        //When
        long result = adapter.countByName("Spider");

        //Then
        assertEquals(2L, result);
    }

    @Test
    void testDeleteById() {
        //Given
        doNothing().when(jpaRepository).deleteById(1L);

        //When
        adapter.deleteById(1L);

        //Then
        verify(jpaRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(jpaRepository, mapper);
    }
}
