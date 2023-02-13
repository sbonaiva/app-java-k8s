package com.bonaiva.app.integration;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.integration.database.CustomerRepository;
import com.bonaiva.app.integration.database.mapper.CustomerEntityMapper;
import com.bonaiva.app.integration.exception.CreateCustomerException;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerIntegration implements CustomerGateway {

    private final CustomerEntityMapper customerEntityMapper;
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(final Customer customer) {
        try {
            final var entityToCreate = customerEntityMapper.toEntity(customer);
            final var createdEntity = customerRepository.saveAndFlush(entityToCreate);
            return customerEntityMapper.fromEntity(createdEntity);
        } catch (Exception e) {
            log.error("Failed to create customer", e);
            throw new CreateCustomerException();
        }
    }
}
