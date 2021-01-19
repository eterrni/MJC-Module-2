package com.epam.esm.service;

import java.util.List;

public interface GiftCertificate<T, K> extends CRD<T, K> {
    void update(final T entity);

    List<T> readByQueryParameters(String tagName, String name, String description, String sortType, String orderType);
}
