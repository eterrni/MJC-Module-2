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
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getInt("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getInt("price"));
        giftCertificate.setDuration(rs.getInt("duration"));


        TimeZone tz = TimeZone.getTimeZone("EAT");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);

        String createDate = df.format(rs.getTimestamp("create_date"));
        String lastUpdateDate = df.format(rs.getTimestamp("last_update_date"));

        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);

        return giftCertificate;
    }
}
