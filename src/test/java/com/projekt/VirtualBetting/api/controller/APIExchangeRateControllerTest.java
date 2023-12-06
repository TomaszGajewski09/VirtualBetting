package com.projekt.VirtualBetting.api.controller;

import com.projekt.VirtualBetting.api.contoller.APIExchangeRateController;
import com.projekt.VirtualBetting.api.facade.APIExchangeRateFacade;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class APIExchangeRateControllerTest {

    @Mock
    private APIExchangeRateFacade exchangeRateFacade;

    @InjectMocks
    private APIExchangeRateController apiExchangeRateController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiExchangeRateController).build();
    }

    @Test
    void getExchangeRate_ShouldReturnExchangeRate_WhenCurrencyCodeIsValid() throws Exception {
        // Given
        String currencyCode = "USD";
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCode(CurrencyType.USD);

        given(exchangeRateFacade.fetchExchangeRate(currencyCode)).willReturn(exchangeRate);

        // When & Then
        mockMvc.perform(get("/api/exchange-rates/{currencyCode}", currencyCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(exchangeRateFacade).fetchExchangeRate(currencyCode);
    }
}

