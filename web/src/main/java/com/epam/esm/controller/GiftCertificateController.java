package com.epam.esm.controller;

import com.epam.esm.converter.OrderTypeConverter;
import com.epam.esm.converter.SortTypeConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateQueryParametersDto;
import com.epam.esm.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Qualifier("giftCertificateService")
    @Autowired
    private IService service;

    @GetMapping("/gift_certificates")
    public List<GiftCertificateDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/gift_certificate/{id}")
    public GiftCertificateDto read(@PathVariable int id) {
        return (GiftCertificateDto) service.read(id);
    }

    @PostMapping("/gift_certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return (GiftCertificateDto) service.create(giftCertificateDto);
    }

    @PutMapping("/gift_certificate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody GiftCertificateDto giftCertificateDto) {
        service.update(giftCertificateDto);
    }

    @DeleteMapping("/gift_certificate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @RequestMapping("/gift_certificatess")
    public List<GiftCertificateDto> readByQueryParameters(@RequestParam(value = "tagName",
            required = false) String tagName, @RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "description",
                                                                  required = false) String description,
                                                          @RequestParam(value = "sortType", required = false)
                                                                  String sortType,
                                                          @RequestParam(value = "orderType", required = false)
                                                                  String orderType) {
        GiftCertificateQueryParametersDto.SortType sortType1 = SortTypeConverter.convert(sortType);
        GiftCertificateQueryParametersDto.OrderType orderType1 = OrderTypeConverter.convert(orderType);
        GiftCertificateQueryParametersDto parametersDto = new GiftCertificateQueryParametersDto(
                tagName, name, description, sortType1, orderType1);
        return service.readByQueryParameters(parametersDto);
    }

}
