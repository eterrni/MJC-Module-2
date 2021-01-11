package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDAO {

    List<GiftCertificate> getAllGiftCertificates();

    void addGiftCertificate(GiftCertificate giftCertificate);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(int id);

    GiftCertificate getGiftCertificate(int id);
}
