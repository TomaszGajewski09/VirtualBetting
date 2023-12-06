package com.projekt.VirtualBetting.api.service;

import com.projekt.VirtualBetting.api.client.APIExchangeRateClient;
import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class APIExchangeRateServiceTest {

    @Mock
    private APIExchangeRateClient exchangeRateClient;

    @InjectMocks
    private APIExchangeRateService exchangeRateService;


    @Test
    public void testFetchExchangeRateAPIByCurrencyCode() {
        // Given
        String currencyCode = "USD";
        ExchangeRateAPI mockResponse = new ExchangeRateAPI();

        // When
        when(exchangeRateClient.getExchangeRates(currencyCode)).thenReturn(mockResponse);

        // Then
        ExchangeRateAPI result = exchangeRateService.fetchExchangeRateAPIByCurrencyCode(currencyCode);
        assertNotNull(result);
        assertEquals(mockResponse, result);
    }
}

