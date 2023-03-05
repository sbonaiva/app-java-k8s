package com.bonaiva.app.usecase;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.exception.CustomerNotFoundException;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCustomer {

    private final CustomerGateway customerGateway;

    public Customer execute(@NonNull final Long id) {
        return customerGateway.get(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
