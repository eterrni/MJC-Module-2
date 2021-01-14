package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto) throws ServiceException {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        Optional<Tag> existingTag = tagDAO.readByName(tag.getName());
        Tag addedTag = existingTag.orElseGet(() -> tagDAO.createTag(tag));
        return modelMapper.map(addedTag, TagDto.class);
    }

    @Override
    @Transactional
    public void deleteTag(int tagId) {
        tagDAO.deleteGiftCertificateHasTag(tagId);
        tagDAO.delete(tagId);
    }

    @Override
    public List<TagDto> readAllTags() {
        List<Tag> tags = tagDAO.readAll();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto readTagById(int tagId) throws ServiceException {
        Optional<Tag> readTag = tagDAO.readById(tagId);
        return readTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ServiceException("There is no tag with ID = " + tagId + " in Database"));
    }

    @Override
    public List<TagDto> readTagsByGiftCertificateId(int giftCertificateId) {
        List<Tag> readTags = tagDAO.readByGiftCertificateId(giftCertificateId);
        return readTags.stream().map(readTag -> modelMapper.map(readTag, TagDto.class)).collect(Collectors.toList());
    }
}
