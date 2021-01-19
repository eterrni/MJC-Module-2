package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.exception.IncorrectParameterValueException;

import java.util.Objects;

public class SortTypeConverter {
    public static GiftCertificateQueryParametersDto.SortType convert(String sortTypeString) {
        GiftCertificateQueryParametersDto.SortType sortType = null;
        if (Objects.nonNull(sortTypeString)) {
            try {
                sortType = GiftCertificateQueryParametersDto.SortType.valueOf(sortTypeString.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IncorrectParameterValueException("Incorrect sort type: " + sortTypeString);
            }
        }
        return sortType;
    }
}
