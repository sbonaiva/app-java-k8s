package com.bonaiva.app.integration;

import com.bonaiva.app.controller.dto.AddressRequestDto;
import com.bonaiva.app.controller.dto.CustomerRequestDto;
import com.bonaiva.app.controller.dto.CustomerResponseDto;
import com.bonaiva.app.domain.Address;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class CustomerControllerTest extends IntegrationTest {

    @Test
    void createCustomerSuccessfully() throws Exception {

        var mockReponseBody = Address.builder()
                .postalCode("37795000")
                .street("Praça Vinte e Dois de Fevereiro")
                .neighborhood("Centro")
                .city("Andradas")
                .state("MG")
                .build();

        stubFor(get(urlPathEqualTo("/api/v1/address"))
                .withQueryParam("postalCode", WireMock.equalTo("37795000"))
                .willReturn(jsonResponse(mockReponseBody, HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        var requestBody = CustomerRequestDto.builder()
                .name("Geralt De Rivia")
                .address(AddressRequestDto.builder()
                        .number("222")
                        .complement("Cs 4")
                        .postalCode("37795000")
                        .build())
                .build();

        webTestClient.post()
                .uri("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseDto.class)
                .value(CustomerResponseDto::getId, notNullValue())
                .value(CustomerResponseDto::getAddress, notNullValue())
                .value(v -> v.getAddress().getPostalCode(), equalTo(mockReponseBody.getPostalCode()))
                .value(v -> v.getAddress().getStreet(), equalTo(mockReponseBody.getStreet()))
                .value(v -> v.getAddress().getNumber(), equalTo(requestBody.getAddress().getNumber()))
                .value(v -> v.getAddress().getComplement(), equalTo(requestBody.getAddress().getComplement()))
                .value(v -> v.getAddress().getNeighborhood(), equalTo(mockReponseBody.getNeighborhood()))
                .value(v -> v.getAddress().getCity(), equalTo(mockReponseBody.getCity()))
                .value(v -> v.getAddress().getState(), equalTo(mockReponseBody.getState()));
    }
}
