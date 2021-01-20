package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.HashMap;
import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @author Alexander Novikov
 */
public interface IGiftCertificateRepository extends ICRDRepository<GiftCertificate, Integer> {
    /**
     * Update entity
     *
     * @param entity modified
     * @return Number of affected rows when updated
     */
    Integer update(final GiftCertificate entity);

    /**
     * The method returns a collection of entities
     *
     * @param parameters HashMap of parameters, where the key is the name of the parameter, and the value is the value of the parameter
     * @return List of found entities
     */
    List<GiftCertificate> readByQueryParameters(HashMap<String, String> parameters);

    /**
     * The method correlates certificates and tags
     *
     * @param list of entities to map
     */
    void joinCertificatesAndTags(List<GiftCertificate> list);
}
