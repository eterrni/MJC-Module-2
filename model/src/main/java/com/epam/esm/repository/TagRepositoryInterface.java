package com.epam.esm.repository;

import java.util.List;

public interface TagRepositoryInterface<T, K> extends CRD<T, K> {
    void joinCertificatesAndTags(List<T> list);
}
