package com.jv.spring.ejemplo.application.usecase;

import com.jv.spring.ejemplo.application.mapper.SuperheroMapper;
import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.core.exception.SuperheroNotFoundException;
import com.jv.spring.ejemplo.core.model.Superhero;
import com.jv.spring.ejemplo.core.ports.outbound.SuperheroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceImplTest {

    @Mock
    private SuperheroRepository superheroRepository;

    @Mock
    private SuperheroMapper superheroMapper;

    @InjectMocks
    private SuperheroServiceImpl superheroService;

    @Test
    void createSuperhero_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(null, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero model = new Superhero(null, "Spider-Man", "Peter Parker", "Web-slinging");
        Superhero saved = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto savedDto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroMapper.toModel(dto)).thenReturn(model);
        when(superheroRepository.save(model)).thenReturn(saved);
        when(superheroMapper.toDto(saved)).thenReturn(savedDto);

        // Act
        SuperheroDto result = superheroService.createSuperhero(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Spider-Man", result.heroName());
        verify(superheroMapper, times(1)).toModel(dto);
        verify(superheroRepository, times(1)).save(model);
        verify(superheroMapper, times(1)).toDto(saved);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findAllPaginated_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findAllPaginated(0, 10)).thenReturn(List.of(hero));
        when(superheroRepository.countAll()).thenReturn(1L);
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroResponse result = superheroService.findAllPaginated(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(1L, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.last());
        verify(superheroRepository, times(1)).findAllPaginated(0, 10);
        verify(superheroRepository, times(1)).countAll();
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findAllPaginated_emptyList_ok() {
        // Arrange
        when(superheroRepository.findAllPaginated(0, 10)).thenReturn(List.of());
        when(superheroRepository.countAll()).thenReturn(0L);

        // Act
        SuperheroResponse result = superheroService.findAllPaginated(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());
        assertTrue(result.last());
        verify(superheroRepository, times(1)).findAllPaginated(0, 10);
        verify(superheroRepository, times(1)).countAll();
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findById_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroDto result = superheroService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(superheroRepository, times(1)).findById(1L);
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findById_notFound_throwsException() {
        // Arrange
        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        SuperheroNotFoundException ex = assertThrows(SuperheroNotFoundException.class,
                () -> superheroService.findById(99L));
        assertEquals("Superhero with id: 99 was not found", ex.getMessage());
        verify(superheroRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findByNamePaginated_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findByNamePaginated("Spider", 0, 10)).thenReturn(List.of(hero));
        when(superheroRepository.countByName("Spider")).thenReturn(1L);
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroResponse result = superheroService.findByNamePaginated("Spider", 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(1L, result.totalElements());
        assertEquals(1, result.totalPages());
        verify(superheroRepository, times(1)).findByNamePaginated("Spider", 0, 10);
        verify(superheroRepository, times(1)).countByName("Spider");
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void findByNamePaginated_noResults_ok() {
        // Arrange
        when(superheroRepository.findByNamePaginated("Xyzzy", 0, 10)).thenReturn(List.of());
        when(superheroRepository.countByName("Xyzzy")).thenReturn(0L);

        // Act
        SuperheroResponse result = superheroService.findByNamePaginated("Xyzzy", 0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());
        assertTrue(result.last());
        verify(superheroRepository, times(1)).findByNamePaginated("Xyzzy", 0, 10);
        verify(superheroRepository, times(1)).countByName("Xyzzy");
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void patchSuperhero_ok() {
        // Arrange
        Superhero existing = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto patch = new SuperheroDto(null, "Amazing Spider-Man", null, null);
        SuperheroDto updatedDto = new SuperheroDto(1L, "Amazing Spider-Man", "Peter Parker", "Web-slinging");
        ArgumentCaptor<Superhero> saveCaptor = ArgumentCaptor.forClass(Superhero.class);

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(superheroRepository.save(any(Superhero.class))).thenReturn(existing);
        when(superheroMapper.toDto(any(Superhero.class))).thenReturn(updatedDto);

        // Act
        SuperheroDto result = superheroService.patchSuperhero(1L, patch);

        // Assert
        verify(superheroRepository).save(saveCaptor.capture());
        Superhero savedHero = saveCaptor.getValue();
        assertEquals(1L, savedHero.getId());
        assertEquals("Amazing Spider-Man", savedHero.getHeroName());
        assertEquals("Peter Parker", savedHero.getRealName());
        assertEquals("Web-slinging", savedHero.getSuperpower());
        assertSame(updatedDto, result);
        verify(superheroRepository, times(1)).findById(1L);
        verify(superheroMapper, times(1)).toDto(any(Superhero.class));
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void patchSuperhero_onlyHeroName_keepsExistingFields() {
        // Arrange — only heroName is provided, the rest must keep existing values
        Superhero existing = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto patch = new SuperheroDto(null, "Scarlet Spider", null, null);
        ArgumentCaptor<Superhero> saveCaptor = ArgumentCaptor.forClass(Superhero.class);

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(superheroRepository.save(any(Superhero.class))).thenReturn(existing);
        when(superheroMapper.toDto(any(Superhero.class)))
                .thenReturn(new SuperheroDto(1L, "Scarlet Spider", "Peter Parker", "Web-slinging"));

        // Act
        superheroService.patchSuperhero(1L, patch);

        // Assert
        verify(superheroRepository).save(saveCaptor.capture());
        Superhero savedHero = saveCaptor.getValue();
        assertEquals("Scarlet Spider", savedHero.getHeroName());
        assertEquals("Peter Parker", savedHero.getRealName());
        assertEquals("Web-slinging", savedHero.getSuperpower());
    }

    @Test
    void patchSuperhero_notFound_throwsException() {
        // Arrange
        SuperheroDto patch = new SuperheroDto(null, "New Name", null, null);

        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        SuperheroNotFoundException ex = assertThrows(SuperheroNotFoundException.class,
                () -> superheroService.patchSuperhero(99L, patch));
        assertEquals("Superhero with id: 99 was not found", ex.getMessage());
        verify(superheroRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void deleteSuperhero_ok() {
        // Arrange
        Superhero existing = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(superheroRepository).deleteById(1L);

        // Act
        superheroService.deleteSuperhero(1L);

        // Assert
        verify(superheroRepository, times(1)).findById(1L);
        verify(superheroRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void deleteSuperhero_notFound_throwsException() {
        // Arrange
        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        SuperheroNotFoundException ex = assertThrows(SuperheroNotFoundException.class,
                () -> superheroService.deleteSuperhero(99L));
        assertEquals("Superhero with id: 99 was not found", ex.getMessage());
        verify(superheroRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }
}
