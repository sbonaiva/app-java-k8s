package com.bonaiva.app.controller;

import com.bonaiva.app.controller.dto.CustomerRequestDto;
import com.bonaiva.app.controller.dto.CustomerResponseDto;
import com.bonaiva.app.controller.mapper.CustomerDtoMapper;
import com.bonaiva.app.usecase.CreateCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerDtoMapper customerDtoMapper;
    private final CreateCustomer createCustomer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto create(@RequestBody @Valid final CustomerRequestDto request)  {
        final var customerToCreate = customerDtoMapper.fromRequestDto(request);
        final var createdCustomer = createCustomer.execute(customerToCreate);
        return customerDtoMapper.toResponseDto(createdCustomer);
    }
}
