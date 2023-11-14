package com.fxmxracingteam.userlib.api;

import com.fxmxracingteam.userlib.dto.AuthDTO;
import com.fxmxracingteam.userlib.dto.UserDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserApiRestService {

    private WebClient webClient;

    @Value("${userRestAPI.baseUrl:http://localhost:8080}")
    private String baseUrl = "http://localhost:8080";

    public WebClient getWebClient() {
        if (webClient == null) {
            this.webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return this.webClient;
    }


    public List<UserDTO> getAllUsers() {
        return getWebClient().get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();
    }

    public UserDTO getUser(String id) {
        return getWebClient().get()
                .uri("/user/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("User not found")))
                .bodyToMono(UserDTO.class)
                .block();
    }

    public UserDTO addUser(UserDTO user) {
        return getWebClient().post()
                .uri("/user")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }

    public UserDTO updateUser(UserDTO user, String id) {
        return getWebClient().put()
                .uri("/user/{id}", id)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }

    public void deleteUser(String id) {
        getWebClient().delete()
                .uri("/user/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public Integer authenticateUser(AuthDTO authDto) {
        return getWebClient().post()
                .uri("/user/login")
                .bodyValue(authDto)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new RuntimeException("Authentication Failed")))
                .bodyToMono(Integer.class)
                .block();
    }
}
