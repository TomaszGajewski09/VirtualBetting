package com.projekt.VirtualBetting.api.mapper;


import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class APIExchangeRateMapper {

    public ExchangeRate mapToExchangeRate(ExchangeRateAPI exchangeRateAPI) {
        if (exchangeRateAPI == null) return null;
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .baseCode(CurrencyType.getIfValid(exchangeRateAPI.getBaseCode()))
                .pln(exchangeRateAPI.getPln())
                .usd(exchangeRateAPI.getUsd())
                .eur(exchangeRateAPI.getEur())
                .gbp(exchangeRateAPI.getGbp())
                .chf(exchangeRateAPI.getChf())
                .jpy(exchangeRateAPI.getJpy())
                .build();
        return exchangeRate;
    }
}
