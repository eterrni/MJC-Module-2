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
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateDAO.getAllGiftCertificates();
    }

    @Override
    @Transactional
    public GiftCertificate getGiftCertificate(int id) {
        return giftCertificateDAO.getGiftCertificate(id);
    }

    @Override
    @Transactional
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateDAO.addGiftCertificate(giftCertificate);
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {

    }

    @Override
    @Transactional
    public void deleteGiftCertificate(int id) {

    }
}
