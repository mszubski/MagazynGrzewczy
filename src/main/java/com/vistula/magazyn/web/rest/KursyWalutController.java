package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.KursyWalutFixerModel;
import com.vistula.magazyn.service.KursyWalutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KursyWalutController {

    private final KursyWalutService kursyWalutService;

    public KursyWalutController(KursyWalutService kursyWalutService) {
        this.kursyWalutService = kursyWalutService;
    }

    @GetMapping("/kursy")
    public KursyWalutFixerModel repository() {
        RestTemplate restTemplate = kursyWalutService.restTemplate();
        KursyWalutFixerModel quote = restTemplate.getForObject(
                "http://data.fixer.io/api/latest?access_key=f31bd3ce82abcc4c83ca376fa80ca6e6&format=1",
            KursyWalutFixerModel.class);

        return quote;
    }
}
