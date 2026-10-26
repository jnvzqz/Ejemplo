package com.jv.spring.ejemplo.contract.service;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;

public interface SuperheroService {

    /**
     * Registers a new superhero in the system.
     *
     * @param superheroDto DTO with the hero data to create.
     * @return The DTO of the persisted superhero.
     */
    SuperheroDto createSuperhero(SuperheroDto superheroDto);

    /**
     * Retrieves a paginated list of all registered superheroes.
     *
     * @param pageNo   Zero-based page number.
     * @param pageSize Maximum number of elements per page.
     * @return Response object encapsulating the list and pagination metadata.
     */
    SuperheroResponse findAllPaginated(int pageNo, int pageSize);

    /**
     * Finds a single superhero by its unique identifier.
     *
     * @param id Numeric identifier of the superhero.
     * @return The DTO of the found superhero.
     * @throws com.jv.spring.ejemplo.core.exception.SuperheroNotFoundException if the ID does not exist.
     */
    SuperheroDto findById(Long id);

    /**
     * Retrieves a paginated list of superheroes whose name partially matches the given text.
     *
     * @param name     Partial or full name to filter by.
     * @param pageNo   Zero-based page number.
     * @param pageSize Maximum number of elements per page.
     * @return Response object with filtered results and pagination metadata.
     */
    SuperheroResponse findByNamePaginated(String name, int pageNo, int pageSize);

    /**
     * Partially updates the properties of an existing superhero.
     *
     * @param id           Numeric identifier of the superhero to update.
     * @param superheroDto DTO containing the fields to update (null fields are ignored).
     * @return The DTO of the superhero reflecting the saved changes.
     */
    SuperheroDto patchSuperhero(Long id, SuperheroDto superheroDto);

    /**
     * Permanently deletes a superhero from the system.
     *
     * @param id Numeric identifier of the superhero to delete.
     */
    void deleteSuperhero(Long id);
}
