package com.epam.esm.configuration;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    private static final String TIMEZONE_EAT = "EAT";
    private static final String PATTERN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";


    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt(ID));
        giftCertificate.setName(rs.getString(NAME));
        giftCertificate.setDescription(rs.getString(DESCRIPTION));
        giftCertificate.setPrice(rs.getInt(PRICE));
        giftCertificate.setDuration(rs.getInt(DURATION));


        TimeZone tz = TimeZone.getTimeZone(TIMEZONE_EAT);
        DateFormat df = new SimpleDateFormat(PATTERN_DATE_FORMAT);
        df.setTimeZone(tz);

        String createDate = df.format(rs.getTimestamp(CREATE_DATE));
        String lastUpdateDate = df.format(rs.getTimestamp(LAST_UPDATE_DATE));

        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);

        return giftCertificate;
    }
}
