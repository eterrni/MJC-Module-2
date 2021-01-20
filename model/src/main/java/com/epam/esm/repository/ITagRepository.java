package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the tag
 */
public interface ITagRepository extends ICRDRepository<Tag, Integer> {
    /**
     * The method correlates certificates and tags
     *
     * @param list of entities to map
     */
    void joinCertificatesAndTags(List<Tag> list);
}
