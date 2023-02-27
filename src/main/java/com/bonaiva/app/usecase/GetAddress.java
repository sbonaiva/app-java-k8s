package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.usecase.exception.AddressNotFoundException;
import com.bonaiva.app.usecase.gateway.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAddress {

    private final AddressGateway addressGateway;

    public Address execute(final String postalCode) {
        return addressGateway.get(postalCode)
                .orElseThrow(() -> new AddressNotFoundException(postalCode));
    }
}
