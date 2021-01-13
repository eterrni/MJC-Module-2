package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    GiftCertificateDAO giftCertificateDAO;

    @Override
    @Transactional
    public List<GiftCertificate> readAllGiftCertificates() {
        return giftCertificateDAO.readAllGiftCertificates();
    }

    @Override
    @Transactional
    public GiftCertificate readGiftCertificate(int id) {
        return giftCertificateDAO.readGiftCertificate(id);
    }

    @Override
    @Transactional
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDAO.createGiftCertificate(giftCertificate);
    }

    @Override
    public Integer updateGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDAO.updateGiftCertificate(giftCertificate);


    }

    @Override
    public void deleteGiftCertificate(int id) {

    }
}