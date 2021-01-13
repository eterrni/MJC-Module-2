package com.epam.esm.entity;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagMapper implements RowMapper<Tag> {

    private static final String ID = "id_tag";
    private static final String NAME = "name_tag";

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Tag(rs.getInt(ID),rs.getString(NAME));
    }
}
