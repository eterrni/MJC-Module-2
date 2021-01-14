package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;
    @Autowired
    private TagService tagService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GiftCertificateDto> readAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.readAllGiftCertificates();
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto readGiftCertificate(int id) throws ServiceException {
        Optional<GiftCertificate> foundCertificate = giftCertificateDAO.readGiftCertificate(id);
        return foundCertificate
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .orElseThrow(() -> new ServiceException("There is no gift certificate with ID = " + id + " in Database"));
    }

    @Override
    @Transactional
    public GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto) {
        createAndSetTags(giftCertificateDto);
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        giftCertificateDAO.createGiftCertificate(giftCertificate);
        giftCertificateDAO.createGiftCertificateHasTag(giftCertificate);
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    // TO DO
    @Override
    public Integer updateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        return giftCertificateDAO.updateGiftCertificate(giftCertificate);
    }

    // TO DO
    @Override
    public void deleteGiftCertificate(int id) {

    }

    private void createAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                TagDto add = tagService.createTag(tagDto);
                tags.add(add);
            }
        }
        giftCertificateDto.setTags(tags);
    }
}