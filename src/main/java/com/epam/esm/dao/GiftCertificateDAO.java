package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import javax.sql.DataSource;
import java.util.List;

public interface GiftCertificateDAO {

    void setDataSource(DataSource dataSource);

    List<GiftCertificate> getAllGiftCertificates();

    void saveGiftCertificate();

    void deleteGiftCertificate(int id);

    GiftCertificate getGiftCertificate(int id);
}
