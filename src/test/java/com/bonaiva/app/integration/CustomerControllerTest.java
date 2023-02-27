package com.bonaiva.app.integration;

import com.bonaiva.app.AbstractTest;
import com.bonaiva.app.controller.dto.request.AddressRequestDto;
import com.bonaiva.app.controller.dto.request.CustomerRequestDto;
import com.bonaiva.app.domain.Address;
import com.bonaiva.app.usecase.GetAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractTest {

    private static final String CUSTOMER_BASE_PATH = "/customers";

    @MockBean
    private GetAddress getAddress;

    @Test
    void createCustomerSuccessfully() throws Exception {

        var requestBody = CustomerRequestDto.builder()
                .name("Geralt De Rivia")
                .address(AddressRequestDto.builder()
                        .postalCode("37795000")
                        .build())
                .build();

        var requestBytes = objectMapper.writeValueAsBytes(requestBody);

        var request = post(CUSTOMER_BASE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBytes);

        when(getAddress.execute("37795000"))
                .thenReturn(Address.builder()
                        .postalCode("37795000")
                        .street("Praça Vinte e Dois de Fevereiro")
                        .neighborhood("Centro")
                        .city("Andradas")
                        .state("MG")
                        .build());

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name", is("Geralt De Rivia")));
    }
}
