package com.projekt.VirtualBetting.api.facade;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.api.mapper.APIExchangeRateMapper;
import com.projekt.VirtualBetting.api.service.APIExchangeRateService;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class APIExchangeRateFacadeTest {

    @Mock
    private APIExchangeRateService exchangeRateService;

    @Mock
    private APIExchangeRateMapper exchangeRateMapper;

    @InjectMocks
    private APIExchangeRateFacade exchangeRateFacade;

    @Test
    public void fetchExchangeRate_ReturnsExchangeRate_WhenCurrencyCodeIsValid() {
        // Given
        String currencyCode = "USD";
        ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI();
        ExchangeRate exchangeRate = new ExchangeRate();

        when(exchangeRateService.fetchExchangeRateAPIByCurrencyCode(currencyCode)).thenReturn(exchangeRateAPI);
        when(exchangeRateMapper.mapToExchangeRate(exchangeRateAPI)).thenReturn(exchangeRate);

        // When
        ExchangeRate result = exchangeRateFacade.fetchExchangeRate(currencyCode);

        // Then
        assertNotNull(result);
        assertEquals(exchangeRate, result);
        verify(exchangeRateService).fetchExchangeRateAPIByCurrencyCode(currencyCode);
        verify(exchangeRateMapper).mapToExchangeRate(exchangeRateAPI);
    }
}

