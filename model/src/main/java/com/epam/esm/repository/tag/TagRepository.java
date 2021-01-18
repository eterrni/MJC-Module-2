package com.epam.esm.repository.tag;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.DatabaseRepository;
import com.epam.esm.repository.certificate.GiftCertificateMapper;
import com.epam.esm.repository.exception.DuplicateNameException;
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
public class TagRepository implements DatabaseRepository<Tag, Integer> {
    private static final String GET_TAG_BY_NAME = "SELECT * FROM mjc_module_2.tag where mjc_module_2.tag.name_tag=?";
    private static final String GET_TAG_BY_ID = "SELECT * FROM mjc_module_2.tag where mjc_module_2.tag.id_tag=?";
    private static final String GET_ALL_TAGS = "SELECT * FROM mjc_module_2.tag";
    public static final String CREATE_TAG = "INSERT INTO `mjc_module_2`.`tag` (`name_tag`) VALUES (?);";
    public static final String DELETE_TAG = "DELETE FROM `mjc_module_2`.`tag` WHERE (`id_tag` = ?);";
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
        return jdbcTemplate.query(GET_ALL_TAGS, tagMapper);
    }

    @Override
    public Optional<Tag> read(final Integer tagId) {
        return jdbcTemplate.query(GET_TAG_BY_ID, new Object[]{tagId}, tagMapper).stream().findFirst();
    }

    @Override
    public Tag create(Tag tag) {
        if (isExist(tag.getName())) {
            throw new DuplicateNameException("A tag with name = " + tag.getName() + " already exists");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(PARAMETER_INDEX_TAG_NAME, tag.getName());
            return preparedStatement;
        }, keyHolder);
        tag.setId(keyHolder.getKey().intValue());
        return tag;
    }

    @Override
    public Integer delete(final Integer tagId) {
        return jdbcTemplate.update(DELETE_TAG, tagId);
    }

    @Override
    public void joinCertificatesAndTags(List<Tag> tags) {
        for (Tag tag : tags) {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(GET_CERTIFICATES_BY_TAG_ID, new Object[]{tag.getId()}, giftCertificateMapper);
            tag.setGiftCertificateList(giftCertificates);
        }
    }

    @Override
    public List<Tag> readByQueryParameters(GiftCertificateQueryParameters parameters) {
        throw new UnsupportedOperationException("");
    }

    public Tag readTagByName(String tagName) {
        Optional<Tag> tag = jdbcTemplate.query(GET_TAG_BY_NAME, new Object[]{tagName}, tagMapper).stream().findAny();
        return tag.get();
    }

    private boolean isExist(String tagName) {
        return jdbcTemplate.query(GET_TAG_BY_NAME, new Object[]{tagName}, tagMapper).stream().findAny().isPresent();
    }

    @Override
    public Integer update(Tag entity) {
        throw new UnsupportedOperationException("Unsupported operation UPDATE for tag");
    }

}
