package com.epam.esm.service;

import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface IService<T, K> {
    List<T> readAll();

    T read(final K id);

    T create(final T entity);

    T update(final T entity);

    void delete(final K id);

}
