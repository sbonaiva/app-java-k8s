package com.bonaiva.app.controller.mapper;

import com.bonaiva.app.controller.dto.request.CustomerRequestDto;
import com.bonaiva.app.controller.dto.response.CustomerResponseDto;
import com.bonaiva.app.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {

    Customer fromRequestDto(CustomerRequestDto request);
    CustomerResponseDto toResponseDto(Customer customer);

}
