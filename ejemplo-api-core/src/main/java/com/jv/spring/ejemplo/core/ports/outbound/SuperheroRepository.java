package com.jv.spring.ejemplo.core.ports.outbound;

import com.jv.spring.ejemplo.core.model.Superhero;

import java.util.List;
import java.util.Optional;

public interface SuperheroRepository {

    /**
     * Saves or updates a superhero in the persistence system.
     *
     * @param superhero Domain model with the data to save.
     * @return The resulting domain model with its assigned ID.
     */
    Superhero save(Superhero superhero);

    /**
     * Finds a superhero by its unique identifier.
     *
     * @param id The provided identifier.
     * @return An Optional containing the superhero if found.
     */
    Optional<Superhero> findById(Long id);

    /**
     * Retrieves a paginated list of all superheroes.
     *
     * @param pageNo   Zero-based page number.
     * @param pageSize Maximum number of elements per page.
     * @return List of domain models for the requested page.
     */
    List<Superhero> findAllPaginated(int pageNo, int pageSize);

    /**
     * Retrieves a paginated list of superheroes whose name contains the given string, case-insensitive.
     *
     * @param name     Search string.
     * @param pageNo   Zero-based page number.
     * @param pageSize Maximum number of elements per page.
     * @return Filtered list of domain models for the requested page.
     */
    List<Superhero> findByNamePaginated(String name, int pageNo, int pageSize);

    /**
     * Returns the total count of superheroes in the system.
     */
    long countAll();

    /**
     * Returns the total count of superheroes matching the given name filter.
     *
     * @param name Search string.
     */
    long countByName(String name);

    /**
     * Physically deletes a superhero from storage by its ID.
     *
     * @param id Unique identifier of the superhero to delete.
     */
    void deleteById(Long id);
}
