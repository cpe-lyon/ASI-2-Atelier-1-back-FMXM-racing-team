package com.fxmxracingteam.cardlib.api;

import com.fxmxracingteam.cardlib.dto.CardDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CardApiRestService {

    private WebClient webClient;

    @Value("${cardRestAPI.baseUrl:http://localhost:8080}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<CardDTO> getAllCards() {
        return webClient.get()
                .uri("/cards")
                .retrieve()
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

    public CardDTO getCard(String id) {
        return webClient.get()
                .uri("/card/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Card not found")))
                .bodyToMono(CardDTO.class)
                .block();
    }

    public List<CardDTO> getRandCard(Integer cardNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/cards/rand")
                        .queryParam("cardNumber", cardNumber)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Card not found")))
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

    public CardDTO addCard(CardDTO card) {
        return webClient.post()
                .uri("/card")
                .bodyValue(card)
                .retrieve()
                .bodyToMono(CardDTO.class)
                .block();
    }

    public CardDTO updateCard(CardDTO card, String id) {
        return webClient.put()
                .uri("/card/{id}", id)
                .bodyValue(card)
                .retrieve()
                .bodyToMono(CardDTO.class)
                .block();
    }

    public void deleteCard(String id) {
        webClient.delete()
                .uri("/card/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<CardDTO> getCardsToSell() {
        return webClient.get()
                .uri("/cards_to_sell")
                .retrieve()
                .bodyToFlux(CardDTO.class)
                .collectList()
                .block();
    }

}
