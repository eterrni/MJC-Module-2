package com.epam.esm.repository;

import java.util.HashMap;
import java.util.List;

public interface GiftCertificateInterface<T, K> extends CRD<T, K> {
    Integer update(final T entity);

    List<T> readByQueryParameters(HashMap<String, String> parameters);

    void joinCertificatesAndTags(List<T> list);
}
