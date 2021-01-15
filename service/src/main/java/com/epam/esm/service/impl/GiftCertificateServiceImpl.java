package com.epam.esm.service.impl;

import com.epam.esm.dao.DatabaseRepository;
import com.epam.esm.dao.exception.DAOException;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.IService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements IService<GiftCertificateDto, Integer> {

    @Qualifier("giftCertificateDAOImpl")
    @Autowired
    private DatabaseRepository databaseRepository;

    //    @Qualifier("tagDAOImpl")
    @Autowired
    private TagDAOImpl tagDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GiftCertificateDto> readAll() {
        List<GiftCertificate> giftCertificates = databaseRepository.readAll();
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto read(final Integer id) throws ServiceException {
        Optional<GiftCertificate> foundCertificate = databaseRepository.read(id);
        return foundCertificate
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new ServiceException("There is no gift certificate with ID = " + id + " in Database"));
    }


    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdGiftCertificate;
        try {
            createAndSetTags(giftCertificateDto);
            createdGiftCertificate = (GiftCertificate) databaseRepository.create(modelMapper.map(giftCertificateDto, GiftCertificate.class));
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) throws ServiceException {
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        GiftCertificateDto readGiftCertificateDto = read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificateDto == null) {
            throw new ServiceException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        GiftCertificate readGiftCertificate = modelMapper.map(readGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate updateGiftCertificate = (GiftCertificate) databaseRepository.update(readGiftCertificate);
        return modelMapper.map(updateGiftCertificate, GiftCertificateDto.class);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void delete(final Integer id) {
        databaseRepository.read(id).orElseThrow(() -> new ServiceException("There is no gift certificate with ID = " + id + " in Database"));
        databaseRepository.deleteGiftCertificateHasTag(id);
        databaseRepository.delete(id);
    }

    @SneakyThrows
    private void createAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            TagDto add;
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                Tag tag = modelMapper.map(tagDto, Tag.class);
                try {
                    add = modelMapper.map(tagDAO.create(tag), TagDto.class);
                    tags.add(add);
                } catch (DAOException tagExist) {
                    add = modelMapper.map(tagDAO.readTagByName(tag.getName()), TagDto.class);
                    tags.add(add);
                }
            }
            giftCertificateDto.setTags(tags);
        }

    }

    private void updateGiftCertificateFields(GiftCertificate readGiftCertificateDto, GiftCertificate
            modifiedGiftCertificate) {
        if (modifiedGiftCertificate.getDuration() != null) {
            readGiftCertificateDto.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (modifiedGiftCertificate.getDescription() != null) {
            readGiftCertificateDto.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (modifiedGiftCertificate.getName() != null) {
            readGiftCertificateDto.setName(modifiedGiftCertificate.getName());
        }
        if (modifiedGiftCertificate.getPrice() != null) {
            readGiftCertificateDto.setPrice(modifiedGiftCertificate.getPrice());
        }
        readGiftCertificateDto.setLastUpdateDate(LocalDateTime.now(ZoneOffset.systemDefault()));
    }
}