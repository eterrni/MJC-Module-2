package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParametersDto {
    @JsonProperty("tagName")
    private String tagName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sortType")
    private SortType sortType;
    @JsonProperty("orderType")
    private OrderType orderType;

    public enum SortType {
        NAME, CREATE_DATE
    }

    public enum OrderType {
        ASC, DESC
    }


}
