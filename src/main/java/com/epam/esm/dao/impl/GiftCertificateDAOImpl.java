package com.epam.esm.dao.impl;

import com.epam.esm.configuration.GiftCertificateMapper;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM mjc_module_2.gift_certificate;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public void saveGiftCertificate() {

    }

    @Override
    public void deleteGiftCertificate(int id) {

    }

    @Override
    public GiftCertificate getGiftCertificate(int id) {
        return null;
    }
}
