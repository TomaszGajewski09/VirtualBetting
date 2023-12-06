package com.projekt.VirtualBetting.service;


import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyConversionService {


    private final ExchangeRateService exchangeRateService;

    public BigDecimal convertCurrency(BigDecimal amount, CurrencyType fromCurrency, CurrencyType toCurrency) throws DomainNotFoundException {
        // Pobranie aktualnego kursu wymiany
        ExchangeRate exchangeRate = exchangeRateService.findActualByBaseCode(fromCurrency.toString());
        BigDecimal valueOfCurrencyToConvert = getValueOfCurrencyToConvert(exchangeRate, toCurrency);

        // Przewalutowanie kwoty
        return amount.multiply(valueOfCurrencyToConvert);
    }

    private BigDecimal getValueOfCurrencyToConvert(ExchangeRate exchangeRate, CurrencyType currency) throws DomainNotFoundException {
        switch (currency) {
            case PLN:
                return exchangeRate.getPln();
            case USD:
                return exchangeRate.getUsd();
            case EUR:
                return exchangeRate.getEur();
            case GBP:
                return exchangeRate.getGbp();
            case CHF:
                return exchangeRate.getChf();
            case JPY:
                return exchangeRate.getJpy();
            default:
                throw new DomainNotFoundException("CurrencyType: " + currency);
        }
    }
}

