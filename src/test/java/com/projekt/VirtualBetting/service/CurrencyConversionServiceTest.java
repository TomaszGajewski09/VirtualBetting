package com.projekt.VirtualBetting.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    @Test
    void convertCurrency_SuccessfulConversion() throws DomainNotFoundException {
        // GIVEN
        BigDecimal amount = BigDecimal.valueOf(100);
        CurrencyType fromCurrency = CurrencyType.USD;
        CurrencyType toCurrency = CurrencyType.EUR;
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setEur(BigDecimal.valueOf(0.85));
        when(exchangeRateService.findActualByBaseCode(fromCurrency.toString())).thenReturn(exchangeRate);

        // WHEN
        BigDecimal result = currencyConversionService.convertCurrency(amount, fromCurrency, toCurrency);

        // THEN
        BigDecimal expected = amount.multiply(BigDecimal.valueOf(0.85));
        assertEquals(expected, result);
        verify(exchangeRateService).findActualByBaseCode(fromCurrency.toString());
    }

    @Test
    void convertCurrency_ExchangeRateServiceThrowsDomainNotFoundException() throws DomainNotFoundException {
        // GIVEN
        BigDecimal amount = BigDecimal.valueOf(100);
        CurrencyType fromCurrency = CurrencyType.USD;
        CurrencyType toCurrency = CurrencyType.EUR;
        when(exchangeRateService.findActualByBaseCode(fromCurrency.toString())).thenThrow(new DomainNotFoundException("CurrencyType: " + fromCurrency));

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            currencyConversionService.convertCurrency(amount, fromCurrency, toCurrency);
        });
    }

}
