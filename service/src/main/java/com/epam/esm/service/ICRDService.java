package com.epam.esm.service;

import java.util.List;

/**
 * Interface that describes CRD (create, read, delete) operations in service layer
 *
 * @param <T> is a generic param which must be inherited from Entity class
 * @param <K> is a generic param which represents a key param
 * @author Alexander Novikov
 */
public interface ICRDService<T, K> {
    /**
     * Get all entities
     *
     * @return List of found entities
     */
    List<T> readAll();

    /**
     * Get entity by id
     *
     * @param id of the entity we want to get
     * @return the entity on this ID
     */
    T read(final K id);

    /**
     * Create a new entity
     *
     * @param entity generic exemplar
     * @return created entity with its ID
     */
    T create(final T entity);

    /**
     * Delete entity by ID
     *
     * @param id of the entity we want to delete
     */
    void delete(final K id);
}
