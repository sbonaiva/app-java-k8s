package com.bonaiva.app.integration;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.usecase.gateway.AddressGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressIntegration implements AddressGateway {

    @Override
    public Optional<Address> get(final String postalCode) {
        return Optional.empty();
    }
}
