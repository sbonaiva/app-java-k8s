package com.bonaiva.app.controller;

import com.bonaiva.app.controller.dto.CustomerRequestDto;
import com.bonaiva.app.controller.dto.CustomerResponseDto;
import com.bonaiva.app.controller.mapper.CustomerDtoMapper;
import com.bonaiva.app.usecase.CreateCustomer;
import com.bonaiva.app.usecase.GetCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerDtoMapper customerDtoMapper;
    private final CreateCustomer createCustomer;
    private final GetCustomer getCustomer;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto get(@PathVariable("id") final Long id) {
        final var customer = getCustomer.execute(id);
        return customerDtoMapper.toResponseDto(customer);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@RequestBody @Valid final CustomerRequestDto request)  {
        final var customerToCreate = customerDtoMapper.fromRequestDto(request);
        final var createdCustomer = createCustomer.execute(customerToCreate);
        return customerDtoMapper.toResponseDto(createdCustomer);
    }
}
