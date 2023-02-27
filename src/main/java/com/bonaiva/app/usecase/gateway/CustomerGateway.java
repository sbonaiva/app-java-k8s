package com.bonaiva.app.usecase.gateway;

import com.bonaiva.app.domain.Customer;

import java.util.Optional;

public interface CustomerGateway {

    Optional<Customer> get(Long id);

    Customer create(Customer customer);

}
