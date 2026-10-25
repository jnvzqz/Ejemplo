package com.jv.spring.ejemplo.contract.service;

import com.jv.spring.ejemplo.contract.dto.SuperheroDto;
import com.jv.spring.ejemplo.contract.dto.SuperheroResponse;

public interface SuperheroService {

    /**
     * Registra un nuevo superhéroe en el sistema.
     *
     * @param superheroDto Objeto de datos con la información del héroe a crear.
     * @return El objeto de datos del superhéroe una vez persistido en el sistema.
     */
    SuperheroDto createSuperhero(SuperheroDto superheroDto);

    /**
     * Recupera una lista paginada de todos los superhéroes registrados.
     *
     * @param pageNo Número de la página solicitada (basado en índice 0).
     * @param pageSize Cantidad de elementos máximos por página.
     * @return Un objeto de respuesta que encapsula la lista de DTOs y la metadata de paginación.
     */
    SuperheroResponse obtenerTodosPaginado(int pageNo, int pageSize);

    /**
     * Busca un único superhéroe por su identificador único.
     *
     * @param id Identificador numérico del superhéroe.
     * @return El DTO del superhéroe si es hallado.
     * @throws RuntimeException (o excepción personalizada) si el identificador no existe.
     */
    SuperheroDto buscarPorId(Long id);

    /**
     * Recupera una lista paginada de superhéroes cuyo nombre coincida de forma parcial con el texto provisto.
     *
     * @param nombre Texto parcial o completo del nombre del superhéroe a filtrar.
     * @param pageNo Número de la página solicitada (basado en índice 0).
     * @param pageSize Cantidad de elementos máximos por página.
     * @return Un objeto de respuesta con los elementos filtrados y su metadata de paginación.
     */
    SuperheroResponse buscarPorNombrePaginado(String nombre, int pageNo, int pageSize);

    /**
     * Modifica parcialmente las propiedades de un superhéroe existente.
     *
     * @param id Identificador numérico del superhéroe a modificar.
     * @param superheroDto DTO que contiene los campos clave que se desean actualizar.
     * @return El DTO del superhéroe reflejando los cambios guardados.
     */
    SuperheroDto patchSuperhero(Long id, SuperheroDto superheroDto);

    /**
     * Elimina de forma física un superhéroe del sistema.
     *
     * @param id Identificador numérico del superhéroe a dar de baja.
     */
    void deleteSuperhero(Long id);
}
