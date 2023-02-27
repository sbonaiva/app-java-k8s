package com.bonaiva.app.integration.database.mapper;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.integration.database.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    CustomerEntity toEntity(Customer customer);
    Customer fromEntity(CustomerEntity entity);

}
