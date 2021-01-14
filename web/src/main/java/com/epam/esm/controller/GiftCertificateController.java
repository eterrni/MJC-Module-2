package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception_handling.ErrorHandler;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gift_certificate")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping
    public List<GiftCertificateDto> readAllGiftCertificate() {
        return service.readAllGiftCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDto readGiftCertificate(@PathVariable int id) throws ServiceException {
        return service.readGiftCertificate(id);
    }

    @PostMapping
    public GiftCertificateDto createNewGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) throws ServiceException {
        return service.createGiftCertificate(giftCertificateDto);
    }

    // TO DO
    @PutMapping
    public void updateGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        service.updateGiftCertificate(giftCertificateDto);
    }

    // TO DO
    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable int id) {
        service.deleteGiftCertificate(id);
    }


    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandler handleIncorrectParameterValueException(ServiceException exception) {
        return new ErrorHandler(exception.getMessage(), 40);
    }

}
