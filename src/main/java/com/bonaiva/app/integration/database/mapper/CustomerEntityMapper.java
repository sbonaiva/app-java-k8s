package com.bonaiva.app.integration.database.mapper;

import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.integration.database.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    CustomerEntity toEntity(Customer customer);
    Customer fromEntity(CustomerEntity entity);

}
