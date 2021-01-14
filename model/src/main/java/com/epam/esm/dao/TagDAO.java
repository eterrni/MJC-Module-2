package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO {
    Optional<Tag> readByName(String name);

    List<Tag> readAll();

    Optional<Tag> readById(int tagId);

    List<Tag> readByGiftCertificateId(int certificateId);

    Tag createTag(Tag tag);

    void delete(int tagId);

    void deleteGiftCertificateHasTag(int id);

}
