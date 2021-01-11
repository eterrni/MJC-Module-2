package com.epam.esm.dao.impl;

import com.epam.esm.configuration.GiftCertificateMapper;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM mjc_module_2.gift_certificate;";
    private static final String SELECT_CERTIFICATE_ID = "SELECT * FROM mjc_module_2.gift_certificate where id=?";
    private static final String INSERT_CERTIFICATE = "INSERT INTO `mjc_module_2`.`gift_certificate` (`name`, `description`, `price`, `duration`) VALUES (?, ?, ?, ?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public GiftCertificate getGiftCertificate(int id) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_ID,
                new Object[]{id}, giftCertificateMapper).stream().findAny().orElse(null);
    }

    @Override
    public void addGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(INSERT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration()
        );
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {

    }

    @Override
    public void deleteGiftCertificate(int id) {

    }
}
