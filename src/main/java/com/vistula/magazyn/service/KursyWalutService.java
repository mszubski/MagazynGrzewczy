package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.KursyWalutFixerModel;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class KursyWalutService {

    List<KursyWalutFixerModel> kursyWalutFixerModels = new ArrayList<>();
    public KursyWalutService() {}

    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }
    public List<KursyWalutFixerModel> getKursyWalutModels() {
        return kursyWalutFixerModels;
    }
}
