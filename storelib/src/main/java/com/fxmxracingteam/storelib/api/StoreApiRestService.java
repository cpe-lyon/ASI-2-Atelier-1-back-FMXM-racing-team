package com.fxmxracingteam.storelib.api;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.fxmxracingteam.storelib.dto.StoreOrderDTO;
import com.fxmxracingteam.storelib.dto.StoreTransactionDTO;

public class StoreApiRestService {
    private WebClient webClient;

    private String baseUrl = "http://localhost:3005/store";

    public WebClient getWebClient() {
        if (webClient == null) {
            this.webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return this.webClient;
    }

    public boolean buyCard(StoreOrderDTO order) {
        return Boolean.TRUE.equals(getWebClient().post()
                .uri("/buy")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    public boolean sellCard(StoreOrderDTO order) {
        return Boolean.TRUE.equals(getWebClient().post()
                .uri("/sell")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    public List<StoreTransactionDTO> getAllTransactions() {
        return getWebClient().get()
                .uri("/transaction")
                .retrieve()
                .bodyToFlux(StoreTransactionDTO.class)
                .collectList()
                .block();
    }
}
