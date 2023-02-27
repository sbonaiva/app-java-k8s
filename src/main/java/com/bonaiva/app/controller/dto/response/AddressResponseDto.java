package com.bonaiva.app.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {

    private String postalCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;

}
