package com.bonaiva.app.integration;

import com.bonaiva.app.controller.dto.AddressRequestDto;
import com.bonaiva.app.controller.dto.CustomerRequestDto;
import com.bonaiva.app.controller.dto.CustomerResponseDto;
import com.bonaiva.app.domain.Address;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.CreateCustomer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class CustomerControllerTest extends IntegrationTest {

    @Autowired
    private CreateCustomer createCustomer;

    @Test
    void createCustomerSuccessfully() {

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

        verify(exactly(1), getRequestedFor(urlPathEqualTo("/api/v1/address"))
                .withQueryParam("postalCode", WireMock.equalTo("37795000")));
    }

    @Test
    void getCustomerSuccessfully() {

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

        var customer = Customer.builder()
                .name("Geralt De Rivia")
                .address(Address.builder()
                        .postalCode("37795000")
                        .number("222")
                        .complement("Cs 4")
                        .build())
                .build();

        var createdCustomer = createCustomer.execute(customer);

        webTestClient.get()
                .uri("/customers/{id}", createdCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponseDto.class)
                .value(CustomerResponseDto::getId, notNullValue())
                .value(CustomerResponseDto::getId, notNullValue())
                .value(CustomerResponseDto::getAddress, notNullValue())
                .value(v -> v.getAddress().getPostalCode(), equalTo(mockReponseBody.getPostalCode()))
                .value(v -> v.getAddress().getStreet(), equalTo(mockReponseBody.getStreet()))
                .value(v -> v.getAddress().getNumber(), equalTo(customer.getAddress().getNumber()))
                .value(v -> v.getAddress().getComplement(), equalTo(customer.getAddress().getComplement()))
                .value(v -> v.getAddress().getNeighborhood(), equalTo(mockReponseBody.getNeighborhood()))
                .value(v -> v.getAddress().getCity(), equalTo(mockReponseBody.getCity()))
                .value(v -> v.getAddress().getState(), equalTo(mockReponseBody.getState()));
    }
}
