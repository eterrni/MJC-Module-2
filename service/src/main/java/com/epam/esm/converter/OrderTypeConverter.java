package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.exception.IncorrectParameterValueException;

import java.util.Objects;

public class OrderTypeConverter {
    public static GiftCertificateQueryParametersDto.OrderType convert(String orderTypeStringFormat) {
        GiftCertificateQueryParametersDto.OrderType orderType = null;
        if (Objects.nonNull(orderTypeStringFormat)) {
            try {
                orderType = GiftCertificateQueryParametersDto.OrderType.valueOf(orderTypeStringFormat.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IncorrectParameterValueException("Incorrect order type: " + orderTypeStringFormat);
            }
        }
        return orderType;
    }
}
