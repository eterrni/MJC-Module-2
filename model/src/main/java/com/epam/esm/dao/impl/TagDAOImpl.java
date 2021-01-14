package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl implements TagDAO {
    private static final String GET_TAG_BY_NAME = "SELECT * FROM mjc_module_2.tag where mjc_module_2.tag.name_tag=?";
    private static final String GET_TAG_BY_ID = "SELECT * FROM mjc_module_2.tag where mjc_module_2.tag.id_tag=?";
    private static final String GET_BY_GIFT_CERTIFICATE_ID = "SELECT * FROM mjc_module_2.tag\n" +
            "join mjc_module_2.gift_certificate_has_tag\n" +
            "on mjc_module_2.tag.id_tag = mjc_module_2.gift_certificate_has_tag.tag_id_tag\n" +
            "where mjc_module_2.gift_certificate_has_tag.gift_certificate_id_gift_certificate = ?;";
    private static final String GET_ALL_TAGS = "SELECT * FROM mjc_module_2.tag";

    public static final String CREATE_TAG = "INSERT INTO `mjc_module_2`.`tag` (`name_tag`) VALUES (?);";
    public static final String DELETE_TAG = "DELETE FROM `mjc_module_2`.`tag` WHERE (`id_tag` = ?);";
    private static final String TAG_DELETE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM mjc_module_2.gift_certificate_has_tag WHERE (tag_id_tag=?)";
    private static final String GET_CERTIFICATES_BY_TAG_ID = "SELECT \n" +
            "mjc_module_2.gift_certificate.id,\n" +
            "mjc_module_2.gift_certificate.name,\n" +
            "mjc_module_2.gift_certificate.description,\n" +
            "mjc_module_2.gift_certificate.price,\n" +
            "mjc_module_2.gift_certificate.duration,\n" +
            "mjc_module_2.gift_certificate.create_date,\n" +
            "mjc_module_2.gift_certificate.last_update_date \n" +
            "FROM mjc_module_2.gift_certificate_has_tag\n" +
            "join mjc_module_2.gift_certificate\n" +
            "on mjc_module_2.gift_certificate_has_tag.gift_certificate_id_gift_certificate = mjc_module_2.gift_certificate.id\n" +
            "where mjc_module_2.gift_certificate_has_tag.tag_id_tag = ?;";

    private static final Integer PARAMETER_INDEX_TAG_NAME = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Override
    public List<Tag> readAll() {
        List<Tag> tags = jdbcTemplate.query(GET_ALL_TAGS, tagMapper);
        for (Tag tag : tags) {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_ID, new Object[]{tag.getId()}, giftCertificateMapper);
            for (GiftCertificate giftCertificate : giftCertificates) {
                tag.getGiftCertificateList().add(new GiftCertificate(giftCertificate.getId(),giftCertificate.getName()));
            }
        }
        return tags;
    }

    @Override
    public Optional<Tag> readByName(String name) {
        return jdbcTemplate.query(GET_TAG_BY_NAME, new Object[]{name}, tagMapper).stream().findAny();
    }

    @Override
    public Optional<Tag> readById(int tagId) {
        Optional<Tag> tag = jdbcTemplate.query(GET_TAG_BY_ID, new Object[]{tagId}, tagMapper).stream().findFirst();
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_ID, new Object[]{tagId}, giftCertificateMapper);
        for(GiftCertificate giftCertificate: giftCertificates){
            tag.get().getGiftCertificateList().add(new GiftCertificate(giftCertificate.getId(), giftCertificate.getName()));
        }
        return tag;
    }

    @Override
    public List<Tag> readByGiftCertificateId(int certificateId) {
        return jdbcTemplate.query(GET_BY_GIFT_CERTIFICATE_ID, new Object[]{certificateId}, tagMapper);
    }

    @Override
    public Tag createTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(PARAMETER_INDEX_TAG_NAME, tag.getName());
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            tag.setId(keyHolder.getKey().intValue());
        }
        return tag;
    }

    @Override
    public void delete(int tagId) {
        jdbcTemplate.update(DELETE_TAG, tagId);
    }

    @Override
    public void deleteGiftCertificateHasTag(int id) {
        jdbcTemplate.update(TAG_DELETE_GIFT_CERTIFICATE_HAS_TAG, id);
    }
}
