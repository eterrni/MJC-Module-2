package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.DatabaseRepository;
import com.epam.esm.repository.tag.TagMapper;
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
public class GiftCertificateRepository implements DatabaseRepository<GiftCertificate, Integer> {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM mjc_module_2.gift_certificate;";
    private static final String SELECT_CERTIFICATE_ID = "SELECT * FROM mjc_module_2.gift_certificate where gift_certificate.id=?;";
    private static final String INSERT_CERTIFICATE = "INSERT INTO `mjc_module_2`.`gift_certificate` (`name`, `description`, `price`, `duration`) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id_tag,name_tag FROM mjc_module_2.gift_certificate_has_tag\n" +
            "join mjc_module_2.tag\n" +
            "on mjc_module_2.gift_certificate_has_tag.tag_id_tag = mjc_module_2.tag.id_tag\n" +
            "where gift_certificate_id_gift_certificate=?;";
    private static final String CREATE_CERTIFICATE_HAS_TAG = "INSERT INTO `mjc_module_2`.`gift_certificate_has_tag` (`gift_certificate_id_gift_certificate`, `tag_id_tag`) VALUES (?, ?);\n";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE `mjc_module_2`.`gift_certificate` SET `name` = ?, `description` = ?, `price` = ?, `duration` = ? WHERE (`id` = ?);\n";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM mjc_module_2.gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM mjc_module_2.gift_certificate_has_tag where gift_certificate_id_gift_certificate=?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GiftCertificateMapper giftCertificateMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public Optional<GiftCertificate> read(final Integer id) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{id}, giftCertificateMapper).stream().findFirst();
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setInt(4, giftCertificate.getDuration());
            return preparedStatement;
        }, keyHolder);

        GiftCertificate createdGiftCertificate = jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{keyHolder.getKey().intValue()}, giftCertificateMapper).stream().findFirst().get();

        giftCertificate.setId(createdGiftCertificate.getId());
        giftCertificate.setCreateDate(createdGiftCertificate.getCreateDate());
        giftCertificate.setLastUpdateDate(createdGiftCertificate.getLastUpdateDate());

        createGiftCertificateHasTag(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Integer update(GiftCertificate giftCertificate) {
        createGiftCertificateHasTag(giftCertificate);
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getId());
    }

    @Override
    public Integer delete(final Integer id) {
        return jdbcTemplate.update(DELETE_GIFT_CERTIFICATE, id);
    }

    @Override
    public void joinCertificatesAndTags(List<GiftCertificate> giftCertificates) {
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{giftCertificate.getId()}, tagMapper);
            giftCertificate.setTags(tags);
        }
    }

    private void createGiftCertificateHasTag(GiftCertificate giftCertificate) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_HAS_TAG, giftCertificate.getId());
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(CREATE_CERTIFICATE_HAS_TAG, giftCertificate.getId(), tag.getId()));
    }
}
