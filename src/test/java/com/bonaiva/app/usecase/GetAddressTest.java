package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.integration.exception.GetAddressException;
import com.bonaiva.app.usecase.exception.AddressNotFoundException;
import com.bonaiva.app.usecase.gateway.AddressGateway;
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
class GetAddressTest {

    @Mock
    private AddressGateway addressGateway;

    @InjectMocks
    private GetAddress getAddress;

    @Test
    void getAddressSuccessfully() {

        var expectedAddress = Address.builder()
                .postalCode("37795000")
                .street("Praça Vinte e Dois de Fevereiro")
                .neighborhood("Centro")
                .city("Andradas")
                .state("MG")
                .build();

        when(addressGateway.get(expectedAddress.getPostalCode()))
                .thenReturn(Optional.of(expectedAddress));

        var actualAddress = getAddress.execute(expectedAddress.getPostalCode());

        assertNotNull(actualAddress);
        assertEquals(expectedAddress, actualAddress);

        verify(addressGateway, only()).get(expectedAddress.getPostalCode());
    }

    @Test
    void getAddressNotFound() {

        var expectedPostalCode  = "37795000";

        when(addressGateway.get(expectedPostalCode)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> getAddress.execute(expectedPostalCode));

        verify(addressGateway, only()).get(expectedPostalCode);
    }

    @Test
    void getAddressException() {

        var expectedPostalCode  = "37795000";

        when(addressGateway.get(expectedPostalCode)).thenThrow(new GetAddressException());

        assertThrows(GetAddressException.class, () -> getAddress.execute(expectedPostalCode));

        verify(addressGateway, only()).get(expectedPostalCode);
    }
}
