package com.bonaiva.app.integration.http.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "services.address")
public class AddressProperties {

    private String baseUrl;
    private Integer timeout;

}
