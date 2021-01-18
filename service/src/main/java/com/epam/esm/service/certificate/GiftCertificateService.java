package com.epam.esm.service.certificate;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.DatabaseRepository;
import com.epam.esm.repository.exception.DuplicateNameException;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.IService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateService implements IService<GiftCertificateDto, Integer> {

    private static final String GIFT_CERTIFICATE_REPOSITORY = "giftCertificateRepository";


    @Qualifier(GIFT_CERTIFICATE_REPOSITORY)
    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private TagRepository tagDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GiftCertificateDto> readAll() {
        List<GiftCertificate> giftCertificates = databaseRepository.readAll();
        databaseRepository.joinCertificatesAndTags(giftCertificates);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto read(final Integer id) {
        Optional<GiftCertificate> readGiftCertificate = databaseRepository.read(id);
        if (readGiftCertificate.toString().equals("Optional.empty")) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else {
            GiftCertificate giftCertificate = readGiftCertificate.get();
            databaseRepository.joinCertificatesAndTags(Collections.singletonList((giftCertificate)));
            return modelMapper.map(giftCertificate, GiftCertificateDto.class);
        }
    }


    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdGiftCertificate;
        createAndSetTags(giftCertificateDto);
        createdGiftCertificate = (GiftCertificate) databaseRepository.create(modelMapper.map(giftCertificateDto, GiftCertificate.class));
        return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public void update(GiftCertificateDto modifiedGiftCertificateDto) {
        GiftCertificateDto readGiftCertificateDto = read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificateDto == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        GiftCertificate readGiftCertificate = modelMapper.map(readGiftCertificateDto, GiftCertificate.class);
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        databaseRepository.update(readGiftCertificate);
    }

    @Override
    @Transactional
    public void delete(final Integer id) {
        if (databaseRepository.delete(id) == 0) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        }
    }

    private void createAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            TagDto add;
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                Tag tag = modelMapper.map(tagDto, Tag.class);
                try {
                    add = modelMapper.map(tagDAO.create(tag), TagDto.class);
                    tags.add(add);
                } catch (DuplicateNameException tagExist) {
                    add = modelMapper.map(tagDAO.readTagByName(tag.getName()), TagDto.class);
                    tags.add(add);
                }
            }
            giftCertificateDto.setTags(tags);
        }

    }

    private void updateGiftCertificateFields(GiftCertificate readGiftCertificate, GiftCertificate
            modifiedGiftCertificate) {
        if (modifiedGiftCertificate.getDuration() != null) {
            readGiftCertificate.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (modifiedGiftCertificate.getDescription() != null) {
            readGiftCertificate.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (modifiedGiftCertificate.getName() != null) {
            readGiftCertificate.setName(modifiedGiftCertificate.getName());
        }
        if (modifiedGiftCertificate.getPrice() != null) {
            readGiftCertificate.setPrice(modifiedGiftCertificate.getPrice());
        }
        if (modifiedGiftCertificate.getTags() != null) {
            GiftCertificateDto modifiedGiftCertificateDto = modelMapper.map(modifiedGiftCertificate, GiftCertificateDto.class);
            createAndSetTags(modifiedGiftCertificateDto);
            readGiftCertificate.setTags(modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class).getTags());
        }
    }
}