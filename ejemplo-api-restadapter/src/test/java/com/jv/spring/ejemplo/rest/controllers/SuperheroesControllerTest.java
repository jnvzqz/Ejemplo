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
    private SuperheroService superheroService;

    @InjectMocks
    private SuperheroesController controller;

    @Test
    void addSuperhero_ok() {
        // Arrange
        SuperheroDto request = new SuperheroDto(null, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroDto saved = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroService.createSuperhero(request)).thenReturn(saved);

        // Act
        ResponseEntity<SuperheroDto> response = controller.addSuperhero(request);

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertSame(saved, response.getBody());
        verify(superheroService, times(1)).createSuperhero(request);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void getAllSuperheroes_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(dto), 0, 10, 1L, 1, true);

        when(superheroService.findAllPaginated(0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getAllSuperheroes(0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertSame(serviceResponse, response.getBody());
        verify(superheroService, times(1)).findAllPaginated(0, 10);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void getAllSuperheroes_emptyList_ok() {
        // Arrange
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(), 0, 10, 0L, 0, true);

        when(superheroService.findAllPaginated(0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getAllSuperheroes(0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().content().isEmpty());
        verify(superheroService, times(1)).findAllPaginated(0, 10);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void getSuperheroById_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroService.findById(1L)).thenReturn(dto);

        // Act
        ResponseEntity<SuperheroDto> response = controller.getSuperheroById(1L);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertSame(dto, response.getBody());
        verify(superheroService, times(1)).findById(1L);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void getSuperheroByName_ok() {
        // Arrange
        SuperheroDto dto = new SuperheroDto(1L, "Spider-Man", "Peter Parker", "Web-slinging");
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(dto), 0, 10, 1L, 1, true);

        when(superheroService.findByNamePaginated("Spider", 0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getSuperheroByName("Spider", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertSame(serviceResponse, response.getBody());
        verify(superheroService, times(1)).findByNamePaginated("Spider", 0, 10);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void getSuperheroByName_noResults_ok() {
        // Arrange
        SuperheroResponse serviceResponse = new SuperheroResponse(List.of(), 0, 10, 0L, 0, true);

        when(superheroService.findByNamePaginated("Xyzzy", 0, 10)).thenReturn(serviceResponse);

        // Act
        ResponseEntity<SuperheroResponse> response = controller.getSuperheroByName("Xyzzy", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().content().isEmpty());
        verify(superheroService, times(1)).findByNamePaginated("Xyzzy", 0, 10);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void patchSuperhero_ok() {
        // Arrange
        SuperheroDto patch = new SuperheroDto(null, "Amazing Spider-Man", null, null);
        SuperheroDto updated = new SuperheroDto(1L, "Amazing Spider-Man", "Peter Parker", "Web-slinging");

        when(superheroService.patchSuperhero(1L, patch)).thenReturn(updated);

        // Act
        ResponseEntity<SuperheroDto> response = controller.patchSuperhero(1L, patch);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertSame(updated, response.getBody());
        verify(superheroService, times(1)).patchSuperhero(1L, patch);
        verifyNoMoreInteractions(superheroService);
    }

    @Test
    void deleteSuperhero_ok() {
        // Arrange
        doNothing().when(superheroService).deleteSuperhero(1L);

        // Act
        ResponseEntity<Void> response = controller.deleteSuperhero(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
        assertNull(response.getBody());
        verify(superheroService, times(1)).deleteSuperhero(1L);
        verifyNoMoreInteractions(superheroService);
    }
}
