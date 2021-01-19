package com.epam.esm.service;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @param <T> is a generic param which must be inherited from Entity class
 * @param <K> is a generic param which represents a key param
 */
public interface GiftCertificateServiceInterface<T, K> extends CRD<T, K> {
    /**
     * Update entity
     *
     * @param entity modified
     */
    void update(final T entity);

    /**
     * Get all entity by search parameters
     *
     * @param tagName     the tag name
     * @param name        the gift certificate name
     * @param description the description
     * @param sortType    the sort type
     * @param orderType   the order type
     * @return List of found gift certificates
     */
    List<T> readByQueryParameters(String tagName, String name, String description, String sortType, String orderType);
}
