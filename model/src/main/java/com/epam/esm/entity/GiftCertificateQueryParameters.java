package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateQueryParameters {
    private String tagName;
    private String name;
    private String description;
    private SortType sortType;
    private OrderType orderType;

    public enum SortType {
        NAME("order by mjc_module_2.gift_certificate.name"),
        CREATE_DATE("order by mjc_module_2.gift_certificate.create_date"),
        DEFAULT("");
        private final String sortType;

        SortType(String sortType) {
            this.sortType = sortType;
        }

        public String getSortType() {
            return sortType;
        }
    }

    public enum OrderType {
        ASC("ASC"),
        DESC("DESC"),
        DEFAULT("");

        private final String orderType;

        OrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderType() {
            return orderType;
        }
    }

}
