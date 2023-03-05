package com.bonaiva.app.integration;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.integration.exception.GetAddressException;
import com.bonaiva.app.integration.http.AddressClient;
import com.bonaiva.app.integration.http.mapper.AddressEntityMapper;
import com.bonaiva.app.usecase.gateway.AddressGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressIntegration implements AddressGateway {

    private final AddressClient addressClient;
    private final AddressEntityMapper addressEntityMapper;

    @Override
    public Optional<Address> get(final String postalCode) {
        try {
            return Optional.ofNullable(postalCode)
                    .map(addressClient::retrieve)
                    .map(addressEntityMapper::fromResponseEntity);
        } catch (final Exception e) {
            log.error("Failed to get address", e);
            throw new GetAddressException();
        }
    }
}
