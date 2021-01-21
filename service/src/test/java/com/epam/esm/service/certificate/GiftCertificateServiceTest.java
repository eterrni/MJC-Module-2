package com.epam.esm.service.certificate;


import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ServiceConfiguration.class})
public class GiftCertificateServiceTest {
    @Autowired
    @Mock
    private ModelMapper modelMapper;

    @Autowired
    @Mock
    private TagRepository tagRepository;

    @Autowired
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        giftCertificateService = new GiftCertificateService(giftCertificateRepository, tagRepository, modelMapper);
    }

    @Test
    public void readAllGiftCertificate_Successful() {
        //given
        Integer giftCertificateID = 1;
        String name = "abc";
        String description = "abc";
        BigDecimal price = BigDecimal.valueOf(12);
        Integer duration = 12;
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime lastUpdateDate = LocalDateTime.now(ZoneId.systemDefault());

        List<Tag> tags = new ArrayList<>();
        List<TagDto> tagDtos = new ArrayList<>();

        GiftCertificate giftCertificate = new GiftCertificate(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tags);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tagDtos);
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        giftCertificateDtoList.add(giftCertificateDto);
        //when
        when(giftCertificateRepository.readAll()).thenReturn(giftCertificates);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn((giftCertificateDto));
        //then
        assertEquals(giftCertificateDtoList, giftCertificateService.readAll());
    }

    @Test
    public void readAllGiftCertificate_TagsNotJoinToCertificates_Unsuccessful() {
        //given
        Integer giftCertificateID = 1;
        String name = "abc";
        String description = "abc";
        BigDecimal price = BigDecimal.valueOf(12);
        Integer duration = 12;
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime lastUpdateDate = LocalDateTime.now(ZoneId.systemDefault());

        List<Tag> tags = new ArrayList<>();
        List<TagDto> tagDtos = new ArrayList<>();

        GiftCertificate giftCertificate = new GiftCertificate(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tags);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);

        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tagDtos);
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        giftCertificateDtoList.add(giftCertificateDto);

        //when
        when(giftCertificateRepository.readAll()).thenReturn(giftCertificates);
        when(modelMapper.map(giftCertificate, GiftCertificateDto.class)).thenReturn((new GiftCertificateDto(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, null)));
        //then
        assertNotEquals(giftCertificateDtoList, giftCertificateService.readAll());
    }

    @Test
    public void readGiftCertificate_Successful() {
        // given
        Integer giftCertificateID = 1;
        String name = "abc";
        String description = "abc";
        BigDecimal price = BigDecimal.valueOf(12);
        Integer duration = 12;
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime lastUpdateDate = LocalDateTime.now(ZoneId.systemDefault());
        List<Tag> tags = new ArrayList<>();
        List<TagDto> tagDtos = new ArrayList<>();

        Optional<GiftCertificate> readGiftCertificateOptional = Optional.of(
                new GiftCertificate(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tags)
        );
        GiftCertificate readGiftCertificate = readGiftCertificateOptional.get();
        GiftCertificateDto readGiftCertificateDto
                = new GiftCertificateDto(giftCertificateID, name, description, price, duration, createdDate, lastUpdateDate, tagDtos);
        // when
        when(giftCertificateRepository.read(giftCertificateID)).thenReturn(readGiftCertificateOptional);
        when(modelMapper.map(readGiftCertificate, GiftCertificateDto.class)).thenReturn(readGiftCertificateDto);
        // then
        assertEquals(readGiftCertificateDto, giftCertificateService.read(1));
    }

    @Test
    public void readGiftCertificateNotExistId_ThrownNotExistIdEntityException() {
        // given
        Integer giftCertificateID = 123;
        // when
        when(giftCertificateRepository.read(giftCertificateID)).thenReturn(Optional.empty());
        // then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.read(giftCertificateID));
    }

    @Test
    public void deleteGiftCertificate_Successful() {
        // given
        Integer giftCertificateID = 1;
        // when
        when(giftCertificateRepository.delete(giftCertificateID)).thenReturn(1);
        //then
        assertDoesNotThrow(() -> giftCertificateService.delete(giftCertificateID));
    }

    @Test
    public void deleteNotExistIdGiftCertificate_ThrownNotExistIdEntityException() {
        // given
        Integer giftCertificateID = 123;
        // when
        when(giftCertificateRepository.delete(giftCertificateID)).thenReturn(0);
        //then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.delete(giftCertificateID));
    }

    @Test
    public void updateGiftCertificate_Successful() {
        // given
        Integer modifiedGiftCertificateId = 1;
        String modifiedGiftCertificateName = "modifiedName";
        GiftCertificate modifiedGiftCertificate = new GiftCertificate();
        modifiedGiftCertificate.setId(modifiedGiftCertificateId);
        modifiedGiftCertificate.setName(modifiedGiftCertificateName);
        GiftCertificateDto modifiedGiftCertificateDto = new GiftCertificateDto();
        modifiedGiftCertificateDto.setId(modifiedGiftCertificateId);
        modifiedGiftCertificateDto.setName(modifiedGiftCertificateName);
        List<Tag> tags = new ArrayList<>();
        List<TagDto> tagsDtos = new ArrayList<>();
        GiftCertificate readGiftCertificate = new GiftCertificate(modifiedGiftCertificateId, "oldName", "description", new BigDecimal(123),
                12, LocalDateTime.now(ZoneId.systemDefault()), LocalDateTime.now(ZoneId.systemDefault()), tags);
        GiftCertificateDto readGiftCertificateDto = new GiftCertificateDto(modifiedGiftCertificateId, "oldName", "description", new BigDecimal(123),
                12, LocalDateTime.now(ZoneId.systemDefault()), LocalDateTime.now(ZoneId.systemDefault()), tagsDtos);
        // when
        when(giftCertificateRepository.read(modifiedGiftCertificateId)).thenReturn(Optional.of(readGiftCertificate));
        when(modelMapper.map(readGiftCertificate, GiftCertificateDto.class)).thenReturn(readGiftCertificateDto);
        when(modelMapper.map(readGiftCertificateDto, GiftCertificate.class)).thenReturn(readGiftCertificate);
        when(modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class)).thenReturn(modifiedGiftCertificate);
        // then
        assertDoesNotThrow(() -> giftCertificateService.update(modifiedGiftCertificateDto));
    }

    @Test
    public void updateGiftCertificate_NotExistId_ThrownNotExistIdException() {
        // given
        Integer modifiedGiftCertificateId = 1321;
        String modifiedGiftCertificateName = "newName";
        GiftCertificateDto modifiedGiftCertificateDto = new GiftCertificateDto();
        modifiedGiftCertificateDto.setId(modifiedGiftCertificateId);
        modifiedGiftCertificateDto.setName(modifiedGiftCertificateName);
        // when
        when(giftCertificateRepository.read(modifiedGiftCertificateId)).thenReturn(Optional.empty());
        // then
        assertThrows(NotExistIdEntityException.class, () -> giftCertificateService.update(modifiedGiftCertificateDto));
    }
}
