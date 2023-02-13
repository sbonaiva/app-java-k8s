package com.bonaiva.app.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    @NotEmpty(message = "attribute 'name' is required")
    @Length(max = 255, message = "attribute 'name' has a maximum of 255 characters")
    private String name;

}
