package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private int id;
    private String name;
    private List<GiftCertificate> giftCertificateList;

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
