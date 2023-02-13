package com.bonaiva.app.usecase.gateway;

import com.bonaiva.app.domain.Customer;

public interface CustomerGateway {

    Customer create(Customer customer);

}
