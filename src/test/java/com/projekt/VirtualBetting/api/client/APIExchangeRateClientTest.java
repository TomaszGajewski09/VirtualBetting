package com.projekt.VirtualBetting.api.client;

import com.projekt.VirtualBetting.api.contoller.APIExchangeRateController;
import com.projekt.VirtualBetting.api.facade.APIExchangeRateFacade;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class APIExchangeRateClientTest {

    @Mock
    private APIExchangeRateFacade exchangeRateFacade;

    @InjectMocks
    private APIExchangeRateController apiExchangeRateController;

    @Test
    void getExchangeRate_ShouldReturnExchangeRate_WhenCurrencyCodeIsValid() {
        // Given
        String currencyCode = "USD";
        ExchangeRate expectedExchangeRate = new ExchangeRate();
        expectedExchangeRate.setBaseCode(CurrencyType.USD);

        given(exchangeRateFacade.fetchExchangeRate(currencyCode)).willReturn(expectedExchangeRate);

        // When
        ExchangeRate actualExchangeRate = apiExchangeRateController.getExchangeRate(currencyCode);

        // Then
        assertNotNull(actualExchangeRate);
        assertEquals(expectedExchangeRate, actualExchangeRate);
        verify(exchangeRateFacade).fetchExchangeRate(currencyCode);
    }
}
