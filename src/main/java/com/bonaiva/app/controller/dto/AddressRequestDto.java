package com.bonaiva.app.controller.dto;

import com.bonaiva.app.controller.annotation.PostalCode;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

    @NotEmpty
    @PostalCode
    private String postalCode;

    private String number;

    private String complement;

}
