package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO {

    List<GiftCertificate> readAllGiftCertificates();

    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    Integer updateGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(int id);

    Optional<GiftCertificate> readGiftCertificate(int id);

    void createGiftCertificateHasTag(GiftCertificate giftCertificate);

}
