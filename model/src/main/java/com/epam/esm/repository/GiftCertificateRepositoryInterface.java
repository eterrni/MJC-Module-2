package com.epam.esm.repository;

import java.util.HashMap;
import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @param <T> is a generic param which must be inherited from Entity class
 * @param <K> is a generic param which represents a key param
 * @author Alexander Novikov
 */
public interface GiftCertificateRepositoryInterface<T, K> extends CRD<T, K> {
    /**
     * Update entity
     *
     * @param entity modified
     * @return Number of affected rows when updated
     */
    Integer update(final T entity);

    /**
     * The method returns a collection of entities
     *
     * @param parameters HashMap of parameters, where the key is the name of the parameter, and the value is the value of the parameter
     * @return List of found entities
     */
    List<T> readByQueryParameters(HashMap<String, String> parameters);

    /**
     * The method correlates certificates and tags
     *
     * @param list of entities to map
     */
    void joinCertificatesAndTags(List<T> list);
}
