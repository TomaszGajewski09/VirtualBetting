package com.projekt.VirtualBetting.api.service;

import com.projekt.VirtualBetting.api.client.APIExchangeRateClient;
import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class APIExchangeRateService {
    private final APIExchangeRateClient exchangeRateClient;

    public ExchangeRateAPI fetchExchangeRateAPIByCurrencyCode(String currencyCode) {
        return exchangeRateClient.getExchangeRates(CurrencyType.getIfValid(currencyCode).toString());
    }
}
