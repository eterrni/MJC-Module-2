package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificate> readAllGiftCertificates();

    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    Integer updateGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(int id);

    GiftCertificate readGiftCertificate(int id);
}
