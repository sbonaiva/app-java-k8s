package com.bonaiva.app.integration;

import com.bonaiva.app.AbstractTest;
import com.bonaiva.app.controller.dto.CustomerRequestDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CustomerControllerTest extends AbstractTest {

    private static final String CUSTOMER_BASE_PATH = "/customers";

    @Test
    void createCustomerSuccessfully() throws Exception {

        var requestBody = CustomerRequestDto.builder()
                .name("Geralt De Rivia")
                .build();

        var requestBytes = objectMapper.writeValueAsBytes(requestBody);

        var request = post(CUSTOMER_BASE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBytes);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name", is("Geralt De Rivia")));
    }
}
