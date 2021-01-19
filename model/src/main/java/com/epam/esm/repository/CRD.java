package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface CRD<T, K> {
    List<T> readAll();

    Optional<T> read(final K id);

    T create(final T entity);

    Integer delete(final K id);
}
