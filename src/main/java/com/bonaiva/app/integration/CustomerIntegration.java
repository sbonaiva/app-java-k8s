package com.bonaiva.app.integration;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.integration.database.CustomerRepository;
import com.bonaiva.app.integration.database.mapper.CustomerEntityMapper;
import com.bonaiva.app.integration.exception.CreateCustomerException;
import com.bonaiva.app.integration.exception.GetCustomerException;
import com.bonaiva.app.usecase.gateway.CustomerGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerIntegration implements CustomerGateway {

    private final CustomerEntityMapper customerEntityMapper;
    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> get(final Long id) {
        try {
            return customerRepository.findById(id)
                    .map(customerEntityMapper::fromEntity);
        } catch (final Exception e) {
            log.error("Failed to retrieve customer", e);
            throw new GetCustomerException();
        }
    }

    @Override
    public Customer create(final Customer customer) {
        try {
            final var entityToCreate = customerEntityMapper.toEntity(customer);
            final var createdEntity = customerRepository.saveAndFlush(entityToCreate);
            return customerEntityMapper.fromEntity(createdEntity);
        } catch (final Exception e) {
            log.error("Failed to create customer", e);
            throw new CreateCustomerException();
        }
    }
}
