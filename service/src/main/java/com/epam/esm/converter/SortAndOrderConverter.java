package com.epam.esm.converter;

import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.exception.IncorrectParameterValueException;

import java.util.Objects;

public final class SortAndOrderConverter {

    private SortAndOrderConverter() {
    }

    public static String sortTypeConverter(String sortTypeString) {
        String sortType = null;
        if (Objects.nonNull(sortTypeString)) {
            try {
                sortType = GiftCertificateQueryParameters.SortType.valueOf(sortTypeString.toUpperCase()).getSortType();
            } catch (IllegalArgumentException ex) {
                throw new IncorrectParameterValueException("Incorrect sort type: " + sortTypeString);
            }
        }
        return sortType;
    }

    public static String orderTypeConverter(String orderTypeStringFormat) {
        String orderType = null;
        if (Objects.nonNull(orderTypeStringFormat)) {
            try {
                orderType = GiftCertificateQueryParameters.OrderType.valueOf(orderTypeStringFormat.toUpperCase()).getOrderType();
            } catch (IllegalArgumentException ex) {
                throw new IncorrectParameterValueException("Incorrect order type: " + orderTypeStringFormat);
            }
        }
        return orderType;
    }
}
