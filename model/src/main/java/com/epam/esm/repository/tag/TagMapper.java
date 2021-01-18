package com.epam.esm.repository.tag;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {

    private static final String TAG_ID = "id_tag";
    private static final String TAG_NAME = "name_tag";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Tag(rs.getInt(TAG_ID), rs.getString(TAG_NAME));
    }
}
