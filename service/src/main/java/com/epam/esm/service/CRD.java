package com.epam.esm.service;

import java.util.List;

public interface CRD<T,K> {
    List<T> readAll();

    T read(final K id);

    T create(final T entity);

    void delete(final K id);
}
