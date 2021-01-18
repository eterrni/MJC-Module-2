package com.epam.esm.service;

import java.util.List;

public interface IService<T, K> {
    List<T> readAll();

    T read(final K id);

    T create(final T entity);

    void update(final T entity);

    void delete(final K id);

}
