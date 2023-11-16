package com.fxmxracingteam.cardlib.api;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fxmxracingteam.cardlib.dto.CardDTO;

import reactor.core.publisher.Mono;

@Service
public class CardApiRestService {

    private WebClient webClient;

    private String baseUrl = "http://proxy:3005/card";

    public WebClient getWebClient() {
        if (webClient == null) {
            this.webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return this.webClient;
    }

    public List<CardDTO> getAllCards() {
        return getWebClient().get()
                .uri("/cards")
                .retrieve()
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

    public CardDTO getCard(String id) {
        return getWebClient().get()
                .uri("/card/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Card not found")))
                .bodyToMono(CardDTO.class)
                .block();
    }

    public List<CardDTO> getRandCard(Integer userId, Integer cardNumber) {
        return getWebClient().get()
                .uri(uriBuilder -> uriBuilder.path("/cards/rand")
                        .queryParam("cardNumber", cardNumber)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Card not found")))
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

    public CardDTO addCard(CardDTO card) {
        return getWebClient().post()
                .uri("/card")
                .bodyValue(card)
                .retrieve()
                .bodyToMono(CardDTO.class)
                .block();
    }

    public CardDTO updateCard(CardDTO card, String id) {
        return getWebClient().put()
                .uri("/card/{id}", id)
                .bodyValue(card)
                .retrieve()
                .bodyToMono(CardDTO.class)
                .block();
    }

    public void deleteCard(String id) {
        getWebClient().delete()
                .uri("/card/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<CardDTO> getCardsToSell() {
        return getWebClient().get()
                .uri("/cards_to_sell")
                .retrieve()
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

}
