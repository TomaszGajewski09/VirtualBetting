package com.projekt.VirtualBetting.api.facade;


import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.api.mapper.APIExchangeRateMapper;
import com.projekt.VirtualBetting.api.service.APIExchangeRateService;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class APIExchangeRateFacade {
    private final APIExchangeRateService exchangeRateService;
    private final APIExchangeRateMapper exchangeRateMapper;

    public ExchangeRate fetchExchangeRate(String currencyCode) {
        ExchangeRateAPI exchangeRateAPI = exchangeRateService.fetchExchangeRateAPIByCurrencyCode(currencyCode);
        return exchangeRateMapper.mapToExchangeRate(exchangeRateAPI);
    }
}
