package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception_handling.ErrorHandler;
import com.epam.esm.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftCertificateController {

    @Qualifier("giftCertificateServiceImpl")
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
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return (GiftCertificateDto) service.update(giftCertificateDto);
    }

    @DeleteMapping("/gift_certificate/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }


    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParameterValueException(ServiceException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }

}
