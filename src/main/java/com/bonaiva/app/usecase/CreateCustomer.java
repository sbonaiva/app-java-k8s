package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCustomer {

    private final CustomerGateway customerGateway;
    private final GetAddress getAddress;

    @Transactional
    public Customer execute(@NonNull final Customer customer) {

        final var address = getAddress.execute(customer.getAddress().getPostalCode());
        customer.enrichWithAddress(address);
        return customerGateway.create(customer);
    }
}
