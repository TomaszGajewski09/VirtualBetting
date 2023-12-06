package com.projekt.VirtualBetting.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class APIExchangeRateConfig {

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;
}
