package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto {
    private Integer id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastUpdateDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TagDto> tags;

}
