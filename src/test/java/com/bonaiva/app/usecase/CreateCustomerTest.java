package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCustomerTest {

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private GetAddress getAddress;

    @InjectMocks
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
