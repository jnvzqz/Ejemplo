package com.jv.spring.ejemplo.application.usecase;

import com.jv.spring.ejemplo.application.mapper.SuperheroMapper;
import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.exception.SuperheroNotFoundException;
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
    void obtenerTodosPaginado_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findAllPaginado(0, 10)).thenReturn(List.of(hero));
        when(superheroRepository.countAll()).thenReturn(1L);
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroResponse result = superheroService.obtenerTodosPaginado(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(1L, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.last());
        verify(superheroRepository, times(1)).findAllPaginado(0, 10);
        verify(superheroRepository, times(1)).countAll();
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void obtenerTodosPaginado_listaVacia_ok() {
        // Arrange
        when(superheroRepository.findAllPaginado(0, 10)).thenReturn(List.of());
        when(superheroRepository.countAll()).thenReturn(0L);

        // Act
        SuperheroResponse result = superheroService.obtenerTodosPaginado(0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0L, result.totalElements());
        assertEquals(0, result.totalPages());
        verify(superheroRepository, times(1)).findAllPaginado(0, 10);
        verify(superheroRepository, times(1)).countAll();
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void buscarPorId_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroDto result = superheroService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(superheroRepository, times(1)).findById(1L);
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void buscarPorId_noEncontrado_lanzaExcepcion() {
        // Arrange
        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SuperheroNotFoundException.class, () -> superheroService.buscarPorId(99L));
        verify(superheroRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void buscarPorNombrePaginado_ok() {
        // Arrange
        Superhero hero = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroRepository.findByHeroNamePaginado("Spider", 0, 10)).thenReturn(List.of(hero));
        when(superheroRepository.countByHeroName("Spider")).thenReturn(1L);
        when(superheroMapper.toDto(hero)).thenReturn(dto);

        // Act
        SuperheroResponse result = superheroService.buscarPorNombrePaginado("Spider", 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(1L, result.totalElements());
        assertEquals(1, result.totalPages());
        verify(superheroRepository, times(1)).findByHeroNamePaginado("Spider", 0, 10);
        verify(superheroRepository, times(1)).countByHeroName("Spider");
        verify(superheroMapper, times(1)).toDto(hero);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }

    @Test
    void buscarPorNombrePaginado_sinResultados_ok() {
        // Arrange
        when(superheroRepository.findByHeroNamePaginado("Xyzzy", 0, 10)).thenReturn(List.of());
        when(superheroRepository.countByHeroName("Xyzzy")).thenReturn(0L);

        // Act
        SuperheroResponse result = superheroService.buscarPorNombrePaginado("Xyzzy", 0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0L, result.totalElements());
        verify(superheroRepository, times(1)).findByHeroNamePaginado("Xyzzy", 0, 10);
        verify(superheroRepository, times(1)).countByHeroName("Xyzzy");
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
    void patchSuperhero_soloHeroName_mantieneCamposExistentes() {
        // Arrange — solo se envía heroName, el resto debe conservar los valores originales
        Superhero existing = new Superhero(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto patch = new SuperheroDto(null, "Scarlet Spider", null, null);
        ArgumentCaptor<Superhero> saveCaptor = ArgumentCaptor.forClass(Superhero.class);

        when(superheroRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(superheroRepository.save(any(Superhero.class))).thenReturn(existing);
        when(superheroMapper.toDto(any(Superhero.class))).thenReturn(new SuperheroDto(1L, "Scarlet Spider", "Peter Parker", "Web-slinging"));

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
    void patchSuperhero_noEncontrado_lanzaExcepcion() {
        // Arrange
        SuperheroDto patch = new SuperheroDto(null, "New Name", null, null);

        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SuperheroNotFoundException.class, () -> superheroService.patchSuperhero(99L, patch));
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
    void deleteSuperhero_noEncontrado_lanzaExcepcion() {
        // Arrange
        when(superheroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SuperheroNotFoundException.class, () -> superheroService.deleteSuperhero(99L));
        verify(superheroRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(superheroRepository, superheroMapper);
    }
}
