package com.projekt.VirtualBetting.api.client;


import com.projekt.VirtualBetting.api.config.APIExchangeRateConfig;
import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class APIExchangeRateClient {

    private final APIExchangeRateConfig exchangeRateConfig;
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(APIFootballClient.class);


    private UriComponentsBuilder getDefaultUrlBuilder() {
        return UriComponentsBuilder.fromHttpUrl(exchangeRateConfig.getApiUrl())
                .pathSegment(exchangeRateConfig.getApiKey());
    }

    public ExchangeRateAPI getExchangeRates(String currencyCode) {
        CurrencyType.getIfValid(currencyCode);

        URI url =  getDefaultUrlBuilder()
                .pathSegment("latest")
                .pathSegment(currencyCode)
                .build()
                .encode()
                .toUri();

        try {
            LOGGER.info("APIExchangeRateClient - getDefaultUrlBuilder() | " + "Initializing request: " + url);
            return restTemplate.getForObject(url, ExchangeRateAPI.class);
        } catch (Exception e) {
            LOGGER.error("APIExchangeRateClient - getDefaultUrlBuilder() | " + e.getMessage(), e);
            return null;
        }
    }
}
