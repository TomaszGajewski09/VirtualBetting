package com.projekt.VirtualBetting.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class APIFootballConfig {

    @Value("${football.api.url}")
    private String apiUrl;

    @Value("${football.api.key}")
    private String apiKey;
}
