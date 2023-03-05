package com.bonaiva.app.usecase.gateway;

import com.bonaiva.app.domain.Address;

import java.util.Optional;

public interface AddressGateway {

    Optional<Address> get(String postalCode);

}
