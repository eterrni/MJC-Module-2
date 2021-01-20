package com.epam.esm.service.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.ICRDService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService implements ICRDService<TagDto, Integer> {

    private TagRepository tagRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> readAll() {
        List<Tag> tags = tagRepository.readAll();
        tagRepository.joinCertificatesAndTags(tags);
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) {
        Optional<Tag> readTag = tagRepository.read(tagId);
        if (readTag.isPresent()) {
            Tag tag = readTag.get();
            tagRepository.joinCertificatesAndTags(Collections.singletonList((tag)));
            return modelMapper.map(tag, TagDto.class);
        } else {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        }
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag addedTag = tagRepository.create(modelMapper.map(tagDto, Tag.class));
        return modelMapper.map(addedTag, TagDto.class);
    }


    @Override
    @Transactional
    public void delete(final Integer tagId) {
        if (tagRepository.delete(tagId) == 0) {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        }
    }

}
