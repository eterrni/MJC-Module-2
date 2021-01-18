package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.util.List;
import java.util.Optional;


public interface DatabaseRepository<T, K> {

    List<T> readAll();

    Optional<T> read(final K id);

    T create(final T entity);

    Integer update(final T entity);

    Integer delete(final K id);

    void joinCertificatesAndTags(List<T> list);

    List<T> readByQueryParameters(GiftCertificateQueryParameters parameters);

}
