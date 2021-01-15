package com.epam.esm.dao;

import com.epam.esm.dao.exception.DAOException;

import java.util.List;
import java.util.Optional;


public interface DatabaseRepository<T, K> {

    List<T> readAll();

    Optional<T> read(final K id);

    T create(final T entity) throws DAOException;

    T update(final T entity);

    void delete(final K id);

    void deleteGiftCertificateHasTag(final K id);

}
