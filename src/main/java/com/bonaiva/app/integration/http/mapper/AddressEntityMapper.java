package com.bonaiva.app.integration.http.mapper;

import com.bonaiva.app.domain.Address;
import com.bonaiva.app.integration.http.entity.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    Address fromResponseEntity(AddressResponse entity);
}
