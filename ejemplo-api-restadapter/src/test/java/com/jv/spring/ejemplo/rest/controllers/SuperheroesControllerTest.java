package com.jv.spring.ejemplo.rest.controllers;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;
import com.jv.spring.ejemplo.contract.service.SuperheroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperheroesControllerTest {

    @Mock
    private SuperheroService superheroesService;

    @InjectMocks
    private SuperheroesController controller;

    @Test
    void addSuperhero_ok() {
        // Arrange
        SuperheroDto request = new SuperheroDto(null, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto saved = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroesService.createSuperhero(request)).thenReturn(saved);

        // Act
        ResponseEntity<SuperheroDto> response = controller.addSuperhero(request);

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertSame(saved, response.getBody());
        verify(superheroesService, times(1)).createSuperhero(request);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getAllSuperheroes_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(dto), 0, 10, 1L, 1, true);

        when(superheroesService.obtenerTodosPaginado(0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getAllSuperheroes(0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertSame(serviceResponse, response.getBody());
        verify(superheroesService, times(1)).obtenerTodosPaginado(0, 10);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getAllSuperheroes_listaVacia_ok() {
        // Arrange
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(), 0, 10, 0L, 0, true);

        when(superheroesService.obtenerTodosPaginado(0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getAllSuperheroes(0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().content().isEmpty());
        verify(superheroesService, times(1)).obtenerTodosPaginado(0, 10);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroById_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroesService.buscarPorId(1L)).thenReturn(dto);

        // Act
        ResponseEntity<SuperheroDto> response = controller.getSuperheroById(1L);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertSame(dto, response.getBody());
        verify(superheroesService, times(1)).buscarPorId(1L);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroByName_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(dto), 0, 10, 1L, 1, true);

        when(superheroesService.buscarPorNombrePaginado("Spider", 0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getSuperheroByName("Spider", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertSame(serviceResponse, response.getBody());
        verify(superheroesService, times(1)).buscarPorNombrePaginado("Spider", 0, 10);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroByName_sinResultados_ok() {
        // Arrange
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(), 0, 10, 0L, 0, true);

        when(superheroesService.buscarPorNombrePaginado("Xyzzy", 0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getSuperheroByName("Xyzzy", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().content().isEmpty());
        verify(superheroesService, times(1)).buscarPorNombrePaginado("Xyzzy", 0, 10);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void patchSuperhero_ok() {
        // Arrange
        SuperheroDto patch = new SuperheroDto(null, "Amazing Spider-Man", null, null);
        SuperheroDto updated = new SuperheroDto(1L, "Amazing Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroesService.patchSuperhero(1L, patch)).thenReturn(updated);

        // Act
        ResponseEntity<SuperheroDto> response = controller.patchSuperhero(1L, patch);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertSame(updated, response.getBody());
        verify(superheroesService, times(1)).patchSuperhero(1L, patch);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void deleteSuperhero_ok() {
        // Arrange
        doNothing().when(superheroesService).deleteSuperhero(1L);

        // Act
        ResponseEntity<Void> response = controller.deleteSuperhero(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(superheroesService, times(1)).deleteSuperhero(1L);
        verifyNoMoreInteractions(superheroesService);
    }
}
