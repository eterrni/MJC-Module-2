package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that describes CRD (create, read, delete) operations in repository layer
 *
 * @param <T> is a generic param which must be inherited from Entity class
 * @param <K> is a generic param which represents a key param
 * @author Alexander Novikov
 */
public interface ICRDRepository<T, K> {
    /**
     * Get all entities from database
     *
     * @return List of found entities
     */
    List<T> readAll();

    /**
     * Get entity
     *
     * @param id of the entity we want to get from database
     * @return the entity on this ID
     */
    Optional<T> read(final K id);

    /**
     * Create a new entity
     *
     * @param entity generic exemplar
     * @return the entity on this ID
     */
    T create(final T entity);

    /**
     * Delete entity by ID
     *
     * @param id of the entity we want to delete
     * @return Number of affected rows when deleted
     */
    Integer delete(final K id);
}
