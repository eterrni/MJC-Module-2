package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.certificate.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping("/gift-certificate/{id}")
    public GiftCertificateDto read(@PathVariable int id) {
        return service.read(id);
    }

    @PostMapping("/gift-certificate")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return service.create(giftCertificateDto);
    }

    @PutMapping("/gift-certificate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody GiftCertificateDto giftCertificateDto) {
        service.update(giftCertificateDto);
    }

    @DeleteMapping("/gift-certificate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping("/gift-certificates")
    public List<GiftCertificateDto> readAll(
            @RequestParam(value = "tagName", required = false) String tagName,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sortType", required = false) String sortType,
            @RequestParam(value = "orderType", required = false) String orderType) {
        return service.readByQueryParameters(tagName, name, description, sortType, orderType);
    }

}
