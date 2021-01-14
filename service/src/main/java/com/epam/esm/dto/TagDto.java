package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GiftCertificateDto> giftCertificateList = new ArrayList<>();

//    public TagDto(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }
}
