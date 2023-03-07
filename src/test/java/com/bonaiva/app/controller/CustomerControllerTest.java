package com.bonaiva.app.controller;

import com.bonaiva.app.controller.dto.AddressRequestDto;
import com.bonaiva.app.controller.dto.CustomerRequestDto;
import com.bonaiva.app.controller.dto.CustomerResponseDto;
import com.bonaiva.app.domain.Address;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.CreateCustomer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToIgnoreCase;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.jsonResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
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
                .withQueryParam("postalCode", equalToIgnoreCase("37795000")));
    }

    @Test
    void createCustomerWithGetAddressError() {

        stubFor(get(urlPathEqualTo("/api/v1/address"))
                .withQueryParam("postalCode", WireMock.equalTo("37795000"))
                .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
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
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(ProblemDetail.class)
                .value(ProblemDetail::getStatus, equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .value(ProblemDetail::getDetail, equalTo("Failed to retrieve address"));

        verify(exactly(1), getRequestedFor(urlPathEqualTo("/api/v1/address"))
                .withQueryParam("postalCode", equalToIgnoreCase("37795000")));
    }

    @ParameterizedTest
    @MethodSource("invalidPostalCodeCases")
    void createCustomerWithPostalCodeValidationError(final String postalCode,
                                                final String error) {

        var requestBody = CustomerRequestDto.builder()
                .name("Geralt De Rivia")
                .address(AddressRequestDto.builder()
                        .number("222")
                        .complement("Cs 4")
                        .postalCode(postalCode)
                        .build())
                .build();

        webTestClient.post()
                .uri("/customers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .value(ProblemDetail::getStatus, equalTo(HttpStatus.BAD_REQUEST.value()))
                .value(ProblemDetail::getDetail, equalTo("Validation failed"))
                .value(ProblemDetail::getProperties, not(anEmptyMap()))
                .value(ProblemDetail::getProperties, hasEntry("address.postalCode", error));
    }

    private static Stream<Arguments> invalidPostalCodeCases() {
        return Stream.of(
                Arguments.of("abcdef", "invalid postal code"),
                Arguments.of("123456789", "invalid postal code"),
                Arguments.of("", "must not be empty"),
                Arguments.of(null, "must not be empty")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidNameCases")
    void createCustomerWithNameValidationError(final String name,
                                          final String error) {

        var requestBody = CustomerRequestDto.builder()
                .name(name)
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
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .value(ProblemDetail::getStatus, equalTo(HttpStatus.BAD_REQUEST.value()))
                .value(ProblemDetail::getDetail, equalTo("Validation failed"))
                .value(ProblemDetail::getProperties, not(anEmptyMap()))
                .value(ProblemDetail::getProperties, hasEntry("name", error));
    }

    private static Stream<Arguments> invalidNameCases() {
        return Stream.of(
                Arguments.of(randomAlphabetic(256), "length must be between 0 and 255"),
                Arguments.of("", "must not be empty"),
                Arguments.of(null, "must not be empty")
        );
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
