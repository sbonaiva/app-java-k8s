package com.bonaiva.app.unit;

import com.bonaiva.app.AbstractTest;
import com.bonaiva.app.domain.Customer;
import com.bonaiva.app.usecase.CreateCustomer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CreateCustomerTest extends AbstractTest {

    private final CreateCustomer createCustomer;

    @Test
    void createCustomerSuccessfully() {

        var expectedCustomer = Customer.builder()
                .name("Geralt de Rivia")
                .build();

        var actualCustomer = createCustomer.execute(expectedCustomer);

        assertEquals(expectedCustomer.getName(), actualCustomer.getName());
    }
}
