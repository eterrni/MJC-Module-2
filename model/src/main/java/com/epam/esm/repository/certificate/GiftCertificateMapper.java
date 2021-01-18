package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt(ID));
        giftCertificate.setName(rs.getString(NAME));
        giftCertificate.setDescription(rs.getString(DESCRIPTION));
        giftCertificate.setPrice(rs.getBigDecimal(PRICE));
        giftCertificate.setDuration(rs.getInt(DURATION));
        giftCertificate.setCreateDate(LocalDateTime.ofInstant(rs.getTimestamp(CREATE_DATE).toInstant(), ZoneId.systemDefault()));
        giftCertificate.setLastUpdateDate(LocalDateTime.ofInstant(rs.getTimestamp(LAST_UPDATE_DATE).toInstant(), ZoneId.systemDefault()));
        return giftCertificate;
    }

}
