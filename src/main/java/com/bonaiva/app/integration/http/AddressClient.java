package com.bonaiva.app.integration.http;

import com.bonaiva.app.integration.http.configuration.AddressProperties;
import com.bonaiva.app.integration.http.entity.AddressResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class AddressClient {

    private final WebClient webClient;

    public AddressClient(final WebClient.Builder webClientBuilder,
                         final AddressProperties addressProperties) {
        this.webClient = webClientBuilder
                .baseUrl(addressProperties.getBaseUrl())
                .build();
    }

    public AddressResponse retrieve(final String postalCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/address")
                        .queryParam("postalCode", postalCode)
                        .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(AddressResponse.class)
                .block();
    }
}
