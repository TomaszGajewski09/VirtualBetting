package com.projekt.VirtualBetting.api.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.api.domains.ExchangeRateAPI;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;



@SpringBootTest
public class APIExchangeRateMapperTest {

    @InjectMocks
    APIExchangeRateMapper mapper;
    @Test
    public void mapToExchangeRate_ValidExchangeRateAPI_ReturnsExchangeRate() {
        // Given
        ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI();
        exchangeRateAPI.setBaseCode("USD");
        exchangeRateAPI.setPln(new BigDecimal(3.5));
        exchangeRateAPI.setUsd(new BigDecimal(1.0));
        exchangeRateAPI.setEur(new BigDecimal(0.9));
        exchangeRateAPI.setGbp(new BigDecimal(0.8));
        exchangeRateAPI.setChf(new BigDecimal(1.1));
        exchangeRateAPI.setJpy(new BigDecimal(110.0));

        // When
        ExchangeRate result = mapper.mapToExchangeRate(exchangeRateAPI);

        // Then
        assertNotNull(result);
        assertEquals(CurrencyType.USD, result.getBaseCode());
        assertEquals(new BigDecimal(3.5), result.getPln());
        assertEquals(new BigDecimal(1.0), result.getUsd());
        assertEquals(new BigDecimal(0.9), result.getEur());
        assertEquals(new BigDecimal(0.8), result.getGbp());
        assertEquals(new BigDecimal(1.1), result.getChf());
        assertEquals(new BigDecimal(110.0), result.getJpy());
    }

    @Test
    public void mapToExchangeRate_NullExchangeRateAPI_ReturnsNull() {
        // Given
        APIExchangeRateMapper mapper = new APIExchangeRateMapper();

        // When
        ExchangeRate result = mapper.mapToExchangeRate(null);

        // Then
        assertNull(result);
    }
}
