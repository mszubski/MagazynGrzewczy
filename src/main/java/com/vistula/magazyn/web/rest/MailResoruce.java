package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.service.email.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller for managing EmailService.
 */
@RestController
@RequestMapping("/api")
public class MailResoruce {

    private final Logger log = LoggerFactory.getLogger(MailResoruce.class);
    public SendEmailService sendEmailService;

    public MailResoruce(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/wyslijEmailKontakt")
    public void uploadFileUmowaRamowa(@RequestParam("dane") String dane,
                                      @RequestParam("temat") String temat,
                                      @RequestParam("email") String email,
                                      @RequestParam("telefon") String telefon,
                                      @RequestParam("poleTekstowe") String poleTekstowe) throws IOException {
        sendEmailService.sendContactMail(dane, temat, email, telefon, poleTekstowe);
    }
}
