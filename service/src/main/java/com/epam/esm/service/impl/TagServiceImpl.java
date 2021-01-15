package com.epam.esm.service.impl;

import com.epam.esm.dao.DatabaseRepository;
import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.IService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements IService<TagDto, Integer> {

    private static final String TAG_DAO_IMPL_BEAN_ID = "tagDAOImpl";

    @Autowired
    @Qualifier(TAG_DAO_IMPL_BEAN_ID)
    private DatabaseRepository databaseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        Tag addedTag;
        try {
            addedTag = (Tag) databaseRepository.create(modelMapper.map(tagDto, Tag.class));
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return modelMapper.map(addedTag, TagDto.class);
    }


    @SneakyThrows
    @Override
    @Transactional
    public void delete(final Integer tagId) {
        databaseRepository.read(tagId).orElseThrow(() -> new ServiceException("There is no tag with ID = " + tagId + " in Database"));
        databaseRepository.deleteGiftCertificateHasTag(tagId);
        databaseRepository.delete(tagId);
    }

    @Override
    public List<TagDto> readAll() {
        List<Tag> tags = databaseRepository.readAll();
        return tags.stream().map(tag -> modelMapper.map(tag, TagDto.class)).collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) throws ServiceException {
        Optional<Tag> readTag = databaseRepository.read(tagId);
        return readTag.map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new ServiceException("There is no tag with ID = " + tagId + " in Database"));
    }

    @Override
    public TagDto update(TagDto entity) {
        return null;
    }

}
