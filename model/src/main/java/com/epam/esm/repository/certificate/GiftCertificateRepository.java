package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.IGiftCertificateRepository;
import com.epam.esm.repository.exception.NotEnoughDataForRegistrationException;
import com.epam.esm.repository.tag.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepository implements IGiftCertificateRepository {

    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_certificate;";
    private static final String SELECT_CERTIFICATE_ID = "SELECT * FROM gift_certificate where gift_certificate.id=?;";
    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id_tag,name_tag FROM mjc_module_2.gift_certificate_has_tag\n" +
            "join mjc_module_2.tag\n" +
            "on mjc_module_2.gift_certificate_has_tag.tag_id_tag = mjc_module_2.tag.id_tag\n" +
            "where gift_certificate_id_gift_certificate=?;";
    private static final String INSERT_CERTIFICATE = "INSERT INTO `gift_certificate` (`name`, `description`, `price`, `duration`) VALUES (?, ?, ?, ?);";
    private static final String CREATE_CERTIFICATE_HAS_TAG = "INSERT INTO `gift_certificate_has_tag` (`gift_certificate_id_gift_certificate`, `tag_id_tag`) VALUES (?, ?);\n";
    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE `gift_certificate` SET `name` = ?, `description` = ?, `price` = ?, `duration` = ? WHERE (`id` = ?);\n";
    private static final String DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag where gift_certificate_id_gift_certificate=?;";
    private static final String GET_BY_QUERY_PARAMETERS = "SELECT \n" +
            "mjc_module_2.gift_certificate.id, mjc_module_2.gift_certificate.name,\n" +
            "mjc_module_2.gift_certificate.description, mjc_module_2.gift_certificate.price,\n" +
            "mjc_module_2.gift_certificate.duration, mjc_module_2.gift_certificate.create_date,\n" +
            "mjc_module_2.gift_certificate.last_update_date \n" +
            "FROM mjc_module_2.gift_certificate \n" +
            "LEFT JOIN mjc_module_2.gift_certificate_has_tag ON mjc_module_2.gift_certificate.id = mjc_module_2.gift_certificate_has_tag.gift_certificate_id_gift_certificate\n" +
            "LEFT JOIN mjc_module_2.tag ON mjc_module_2.gift_certificate_has_tag.tag_id_tag = mjc_module_2.tag.id_tag \n" +
            "WHERE mjc_module_2.tag.name_tag LIKE concat(?, '%') AND \n" +
            "mjc_module_2.gift_certificate.name LIKE concat(?, '%') AND\n" +
            "mjc_module_2.gift_certificate.description LIKE concat(?, '%') GROUP BY mjc_module_2.gift_certificate.id ";

    private JdbcTemplate jdbcTemplate;

    private GiftCertificateMapper giftCertificateMapper;

    private TagMapper tagMapper;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<GiftCertificate> readAll() {
        return jdbcTemplate.query(SELECT_ALL_CERTIFICATES, giftCertificateMapper);
    }

    @Override
    public Optional<GiftCertificate> read(final Integer id) {
        return jdbcTemplate.query(SELECT_CERTIFICATE_ID, new Object[]{id}, giftCertificateMapper).stream().findFirst();
    }

    @Override
    public List<GiftCertificate> readByQueryParameters(HashMap<String, String> parameters) {
        String query = GET_BY_QUERY_PARAMETERS + parameters.get("sortType") +
                " " + parameters.get("orderType");
        return jdbcTemplate.query(query, new Object[]{
                parameters.get("tagName"),
                parameters.get("name"),
                parameters.get("description"),
        }, giftCertificateMapper);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, giftCertificate.getName());
                preparedStatement.setString(2, giftCertificate.getDescription());
                preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
                preparedStatement.setInt(4, giftCertificate.getDuration());
                return preparedStatement;
            }, keyHolder);
        } catch(Exception e){
            throw new NotEnoughDataForRegistrationException("Invalid parameters for creating a gift certificate. To create a certificate, you must write the name, description, price, and duration of the certificate");
        }
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
        if (giftCertificate.getTags() != null) {
            List<Tag> tags = giftCertificate.getTags();
            tags.forEach(tag -> jdbcTemplate.update(CREATE_CERTIFICATE_HAS_TAG, giftCertificate.getId(), tag.getId()));
        }
    }
}
