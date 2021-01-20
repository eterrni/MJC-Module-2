package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 */
public interface IGiftCertificateService extends ICRDService<GiftCertificateDto, Integer> {
    /**
     * Update entity
     *
     * @param entity modified
     */
    void update(final GiftCertificateDto entity);

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
    List<GiftCertificateDto> readByQueryParameters(String tagName, String name, String description, String sortType, String orderType);
}
