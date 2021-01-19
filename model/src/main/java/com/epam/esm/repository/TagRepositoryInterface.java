package com.epam.esm.repository;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the tag
 *
 * @param <T> is a generic param which must be inherited from Entity class
 * @param <K> is a generic param which represents a key param
 */
public interface TagRepositoryInterface<T, K> extends CRD<T, K> {
    /**
     * The method correlates certificates and tags
     *
     * @param list of entities to map
     */
    void joinCertificatesAndTags(List<T> list);
}
