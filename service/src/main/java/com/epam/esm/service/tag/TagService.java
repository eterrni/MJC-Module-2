package com.epam.esm.service.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.DatabaseRepository;
import com.epam.esm.service.IService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService implements IService<TagDto, Integer> {

    private static final String TAG_DAO_IMPL_BEAN_ID = "tagRepository";

    @Autowired
    @Qualifier(TAG_DAO_IMPL_BEAN_ID)
    private DatabaseRepository databaseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TagDto> readAll() {
        List<Tag> tags = databaseRepository.readAll();
        databaseRepository.joinCertificatesAndTags(tags);
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) {
        Optional<Tag> readTag = databaseRepository.read(tagId);
        if (readTag.toString().equals("Optional.empty")) {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        } else {
            Tag tag = readTag.get();
            databaseRepository.joinCertificatesAndTags(Collections.singletonList((tag)));
            return modelMapper.map(tag, TagDto.class);
        }
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag addedTag = (Tag) databaseRepository.create(modelMapper.map(tagDto, Tag.class));
        return modelMapper.map(addedTag, TagDto.class);
    }


    @Override
    @Transactional
    public void delete(final Integer tagId) {
        if (databaseRepository.delete(tagId) == 0) {
            throw new NotExistIdEntityException("There is no tag with ID = " + tagId + " in Database");
        }
    }

    @Override
    public void update(TagDto entity) {
        throw new UnsupportedOperationException("Unsupported operation UPDATE for tag");
    }

}
