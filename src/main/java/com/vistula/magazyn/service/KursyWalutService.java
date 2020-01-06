package com.vistula.magazyn.service;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KursyWalutService {

    public KursyWalutService() {}

    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }
}
