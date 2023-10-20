package com.fxmxracingteam.storelib.api;

import com.fxmxracingteam.storelib.dto.StoreOrderDTO;
import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class StoreApiRestService {
    private WebClient webClient;

    @Value("${storeRestAPI.baseUrl:http://localhost:8080}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public boolean buyCard(StoreOrderDTO order) {
        return Boolean.TRUE.equals(webClient.post()
                .uri("/buy")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    public boolean sellCard(StoreOrderDTO order) {
        return Boolean.TRUE.equals(webClient.post()
                .uri("/sell")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    public List<StoreTransactionDTO> getAllTransactions() {
        return webClient.get()
                .uri("/transaction")
                .retrieve()
                .bodyToFlux(StoreTransactionDTO.class)
                .collectList()
                .block();
    }
}
