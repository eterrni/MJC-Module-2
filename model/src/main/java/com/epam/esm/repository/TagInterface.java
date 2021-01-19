package com.epam.esm.repository;

import java.util.List;

public interface TagInterface<T, K> extends CRD<T, K> {
    void joinCertificatesAndTags(List<T> list);
}
