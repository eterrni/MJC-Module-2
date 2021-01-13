package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GiftCertificate> giftCertificateList = new ArrayList<>();

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
