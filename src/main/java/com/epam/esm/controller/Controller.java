package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception_handling.NoSuchGiftCertificateException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping("/gift_certificates")
    public List<GiftCertificate> showAllGiftCertificate() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping("/gift_certificates/{id}")
    public GiftCertificate getGiftCertificate(@PathVariable int id) {
        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificate(id);
        if (giftCertificate == null) {
            throw new NoSuchGiftCertificateException("There is no gift certificate with ID = " + id + " in Database");
        }
        return giftCertificate;
    }

    @PostMapping("/gift_certificates")
    public void addNewGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.addGiftCertificate(giftCertificate);
    }

    @PutMapping("/gift_certificates")
    public void updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.updateGiftCertificate(giftCertificate);
    }
}
