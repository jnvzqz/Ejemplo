package com.jv.spring.ejemplo.core.ports.outbound;

import com.jv.spring.ejemplo.core.model.Superhero;
import java.util.List;
import java.util.Optional;

public interface SuperheroRepository {

    /**
     * Registra o actualiza un superhéroe en el sistema de persistencia.
     *
     * @param superhero Modelo de dominio con los datos a guardar.
     * @return El modelo de dominio resultante con su ID asignado.
     */
    Superhero save(Superhero superhero);

    /**
     * Busca un superhéroe por su identificador único.
     *
     * @param id El identificador proporcionado.
     * @return Un Optional que contiene el superhéroe si existe.
     */
    Optional<Superhero> findById(Long id);

    /**
     * Recupera una lista paginada de todos los superhéroes del sistema.
     *
     * @param pageNo Número de la página solicitada (basado en índice 0).
     * @param pageSize Cantidad de elementos máximos por página.
     * @return Lista de modelos de dominio correspondientes a la página solicitada.
     */
    List<Superhero> findAllPaginado(int pageNo, int pageSize);

    /**
     * Recupera una lista paginada de superhéroes cuyo nombre contenga una cadena específica, ignorando mayúsculas.
     *
     * @param heroName La cadena de búsqueda.
     * @param pageNo Número de la página solicitada (basado en índice 0).
     * @param pageSize Cantidad de elementos máximos por página.
     * @return Lista filtrada de modelos de dominio correspondientes a la página.
     */
    List<Superhero> findByHeroNamePaginado(String heroName, int pageNo, int pageSize);

    /**
     * Devuelve el recuento total de superhéroes registrados en el sistema.
     */
    long countAll();

    /**
     * Devuelve el recuento total de superhéroes que coinciden con el filtro de nombre provisto.
     *
     * @param heroName La cadena de búsqueda.
     */
    long countByHeroName(String heroName);

    /**
     * Elimina físicamente un superhéroe del almacenamiento mediante su ID.
     *
     * @param id Identificador único del superhéroe a borrar.
     */
    void deleteById(Long id);
}
