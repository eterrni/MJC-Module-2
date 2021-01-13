package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM mjc_module_2.gift_certificate;";
    private static final String SELECT_CERTIFICATE_ID = "SELECT * FROM mjc_module_2.gift_certificate where gift_certificate.id=?;";
    private static final String INSERT_CERTIFICATE = "INSERT INTO `mjc_module_2`.`gift_certificate` (`name`, `description`, `price`, `duration`) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id_tag,name_tag FROM mjc_module_2.gift_certificate_has_tag\n" +
            "join mjc_module_2.tag\n" +
            "on mjc_module_2.gift_certificate_has_tag.tag_id_tag = mjc_module_2.tag.id_tag\n" +
            "where gift_certificate_id_gift_certificate=?;";

    private static final String SELECT_ALL_TAGS = "SELECT * FROM mjc_module_2.tag";
    private static final String SELECT_TAG_ID = "SELECT * FROM mjc_module_2.tag where mjc_module_2.tag.id_tag=?;";
    private static final String INSERT_TAG = "INSERT INTO `mjc_module_2`.`tag` (`name_tag`) VALUES (?);";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<GiftCertificate> readAllGiftCertificates() {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
        for (GiftCertificate giftCertificate : giftCertificateList) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{giftCertificate.getId()}, tagMapper);
            for (Tag tag : tags) {
                giftCertificate.getTags().add(tag);
            }
        }
        return giftCertificateList;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) {
        GiftCertificate giftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID,
                new Object[]{id}, giftCertificateMapper).stream().findAny().orElse(null);
        if (giftCertificate != null) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{id}, tagMapper);
            for (Tag tag : tags) {
                giftCertificate.getTags().add(tag);
            }
            return giftCertificate;
        } else return null;
    }


    @Override
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(INSERT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration()
        );
        return giftCertificate; // заглушка
    }

    @Override
    public Integer updateGiftCertificate(GiftCertificate giftCertificate) {
        return 0;
    }

    @Override
    public void deleteGiftCertificate(int id) {

    }
}
