package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.KursyWalutFixerModel;
import com.vistula.magazyn.domain.KursyWalutSymboleFixerModel;
import com.vistula.magazyn.service.KursyWalutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class KursyWalutController {

    private final KursyWalutService kursyWalutService;

    public KursyWalutController(KursyWalutService kursyWalutService) {
        this.kursyWalutService = kursyWalutService;
    }

    private String apiAcessKey = "f31bd3ce82abcc4c83ca376fa80ca6e6";

    @GetMapping("/kursy")
    public KursyWalutFixerModel getAllKursyWalut() {
        RestTemplate restTemplate = kursyWalutService.restTemplate();
        KursyWalutFixerModel quote = restTemplate.getForObject(
            "http://data.fixer.io/api/latest?access_key=" + apiAcessKey + "&format=1",
            KursyWalutFixerModel.class);

        return quote;
    }

    @GetMapping("/kursy/symbole")
    public KursyWalutSymboleFixerModel getAllKursyWalutSymbole() {
        RestTemplate restTemplate = kursyWalutService.restTemplate();
        KursyWalutSymboleFixerModel quote = restTemplate.getForObject(
            "http://data.fixer.io/api/symbols?access_key=" + apiAcessKey + "&format=1",
            KursyWalutSymboleFixerModel.class);

        return quote;
    }
}
