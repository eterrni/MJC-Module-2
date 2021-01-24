package com.epam.esm.service.tag;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.exception.DuplicateNameException;
import com.epam.esm.repository.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class TagServiceTest {

    @Autowired
    @Mock
    private ModelMapper modelMapper;

    @Autowired
    @Mock
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagService(tagRepository, modelMapper);
    }

    @Test
    public void readAll_returnsTheExpectedResult_test() {
        // given
        Integer tagId = 1;
        String name = "tagName";

        List<Tag> readTags = new ArrayList<>();
        Tag tag = new Tag(tagId, name);
        readTags.add(tag);

        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(tagId, name);
        tagDtos.add(tagDto);

        // when
        when(tagRepository.readAll()).thenReturn(readTags);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        // then
        assertEquals(tagDtos, tagService.readAll());
    }

    @Test
    public void readAll_tagsNotJoinToCertificates_failedExecution_test() {
        // given
        Integer tagId = 1;
        String name = "tagName";

        List<Tag> readTags = new ArrayList<>();
        Tag tag = new Tag(tagId, name);
        readTags.add(tag);

        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(tagId, "tagName");
        tagDtos.add(tagDto);

        // when
        when(tagRepository.readAll()).thenReturn(readTags);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(new TagDto(1, "newTagName"));
        // then
        assertNotEquals(tagDtos, tagService.readAll());
    }

    @Test
    public void read_returnsTheExpectedResult_test() {
        // given
        Integer tagId = 1;
        String name = "tagName";
        TagDto expected = new TagDto(tagId, name);
        Optional<Tag> tag = Optional.of(new Tag(tagId, name));
        // when
        when(tagRepository.read(tagId)).thenReturn(tag);
        when(modelMapper.map(tag.get(), TagDto.class)).thenReturn(expected);
        // then
        assertEquals(expected, tagService.read(tagId));
    }

    @Test
    public void read_notExistId_thrownNotExistIdEntityException_test() {
        // given
        Integer tagId = 123;
        // when
        when(tagRepository.read(tagId)).thenReturn(Optional.empty());
        // then
        assertThrows(NotExistIdEntityException.class, () -> tagService.read(tagId));
    }

    @Test
    public void delete_theEntityWasRemovedFromTheDatabase_test() {
        // given
        Integer tagId = 1;
        // when
        when(tagRepository.delete(tagId)).thenReturn(1);
        // then
        assertDoesNotThrow(() -> tagService.delete(tagId));
    }

    @Test
    public void delete_notExistId_thrownNotExistIdException_test() {
        // given
        Integer tagId = 123;
        // when
        when(tagRepository.delete(tagId)).thenReturn(0);
        // then
        assertThrows(NotExistIdEntityException.class, () -> tagService.delete(tagId));
    }

    @Test
    public void create_entityWasCreatedInDatabase_test() {
        // given
        TagDto addedTagDto = new TagDto(0, "newTag");
        Tag addedTag = new Tag(0, "newTag");
        Tag createdTag = new Tag(1, "newTag");
        TagDto expected = new TagDto(1, "newTag");
        // when
        when(modelMapper.map(addedTagDto, Tag.class)).thenReturn(addedTag);
        when(tagRepository.create(addedTag)).thenReturn(createdTag);
        when(modelMapper.map(createdTag, TagDto.class)).thenReturn(expected);
        // then
        assertEquals(expected, tagService.create(addedTagDto));
    }

    @Test
    public void create_duplicateTagName_thrownDuplicateNameException_test() {
        // given
        TagDto addedTagDto = new TagDto(0, "duplicateName");
        Tag addedTag = new Tag(0, "newTag");
        // when
        when(modelMapper.map(addedTagDto, Tag.class)).thenReturn(addedTag);
        when(tagRepository.create(addedTag)).thenThrow(DuplicateNameException.class);
        // then
        assertThrows(DuplicateNameException.class, () -> tagService.create(addedTagDto));
    }

}
