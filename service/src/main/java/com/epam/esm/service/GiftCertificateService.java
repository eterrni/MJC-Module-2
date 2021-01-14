package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> readAllGiftCertificates();

    GiftCertificateDto createGiftCertificate(GiftCertificateDto giftCertificateDto);

    Integer updateGiftCertificate(GiftCertificateDto giftCertificateDto);

    void deleteGiftCertificate(int id);

    GiftCertificateDto readGiftCertificate(int id);
}
