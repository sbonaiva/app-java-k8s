package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.integration.exception.GetCustomerException;
import com.bonaiva.app.usecase.exception.CustomerNotFoundException;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomerTest {

    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private GetCustomer getCustomer;

    @Test
    void getCustomerSuccessfully() {

        var expectedCustomer = Customer.builder()
                .id(1L)
                .name("Geralt de Rivia")
                .address(Address.builder()
                        .postalCode("37795000")
                        .street("Praça Vinte e Dois de Fevereiro")
                        .neighborhood("Centro")
                        .city("Andradas")
                        .state("MG")
                        .build())
                .build();

        when(customerGateway.get(expectedCustomer.getId()))
                .thenReturn(Optional.of(expectedCustomer));

        var actualCustomer = getCustomer.execute(expectedCustomer.getId());

        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer, actualCustomer);

        verify(customerGateway, only()).get(expectedCustomer.getId());
    }

    @Test
    void getCustomerNotFound() {

        var expectedCustomer = 1L;

        when(customerGateway.get(expectedCustomer))
                .thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> getCustomer.execute(expectedCustomer));

        verify(customerGateway, only()).get(expectedCustomer);
    }

    @Test
    void getCustomerException() {

        var expectedCustomer = 1L;

        when(customerGateway.get(expectedCustomer))
                .thenThrow(new GetCustomerException());

        assertThrows(GetCustomerException.class, () -> getCustomer.execute(expectedCustomer));

        verify(customerGateway, only()).get(expectedCustomer);
    }
}
