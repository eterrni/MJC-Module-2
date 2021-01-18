package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;

import java.util.List;

public interface IService<T, K> {
    List<T> readAll();

    T read(final K id);

    T create(final T entity);

    void update(final T entity);

    void delete(final K id);

    List<T> readByQueryParameters(GiftCertificateQueryParametersDto parameters);
}
