package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificate> getAllGiftCertificates();

    void saveGiftCertificate();

    void deleteGiftCertificate(int id);

    GiftCertificate getGiftCertificate(int id);
}
