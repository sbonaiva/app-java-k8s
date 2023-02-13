package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCustomer {

    private final CustomerGateway customerGateway;

    @Transactional
    public Customer execute(final Customer customer) {
        return customerGateway.create(customer);
    }
}
