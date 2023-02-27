package com.bonaiva.app.unit;

import com.bonaiva.app.AbstractTest;
import com.bonaiva.app.domain.Address;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.CreateCustomer;
import com.bonaiva.app.usecase.GetAddress;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateCustomerTest extends AbstractTest {

    @MockBean
    private CustomerGateway customerGateway;

    @MockBean
    private GetAddress getAddress;

    @Autowired
    private CreateCustomer createCustomer;

    @Test
    void createCustomerSuccessfully() {

        var expectedAddress = Address.builder()
                .postalCode("37795000")
                .street("Praça Vinte e Dois de Fevereiro")
                .neighborhood("Centro")
                .city("Andradas")
                .state("MG")
                .build();

        var expectedCustomer = Customer.builder()
                .name("Geralt de Rivia")
                .address(expectedAddress)
                .build();

        when(getAddress.execute(expectedAddress.getPostalCode()))
                .thenReturn(expectedAddress);

        when(customerGateway.create(any(Customer.class)))
                .thenReturn(expectedCustomer);

        var actualCustomer = createCustomer.execute(expectedCustomer);

        assertEquals(expectedCustomer.getName(), actualCustomer.getName());
        assertNotNull(actualCustomer.getAddress());

        verify(getAddress, only()).execute(expectedAddress.getPostalCode());
        verify(customerGateway, only()).create(any(Customer.class));
    }
}
