package com.bonaiva.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private Address address;

    public void enrichWithAddress(final Address address) {

        if (Objects.isNull(this.address)) {
            this.address = new Address();
        }

        this.address.setStreet(address.getStreet());
        this.address.setNeighborhood(address.getNeighborhood());
        this.address.setCity(address.getCity());
        this.address.setState(address.getState());
    }
}
