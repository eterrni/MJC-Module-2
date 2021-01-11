package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
