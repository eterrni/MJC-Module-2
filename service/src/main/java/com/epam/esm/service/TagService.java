package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface TagService {
    TagDto createTag(TagDto tagDto) throws ServiceException;

    void deleteTag(int tagId);

    List<TagDto> readAllTags();

    TagDto readTagById(int tagId) throws ServiceException;

    List<TagDto> readTagsByGiftCertificateId(int giftCertificateId);

}
