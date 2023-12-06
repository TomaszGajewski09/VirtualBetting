package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.mapper.ExchangeRateMapper;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.dto.ExchangeRateDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateMapperTest {

    @InjectMocks
    private ExchangeRateMapper exchangeRateMapper;

    @Test
    void mapToExchangeRateDTO_ShouldMapCorrectly() {
        // GIVEN
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCodeId(1L);
        exchangeRate.setBaseCode(CurrencyType.USD);
        exchangeRate.setUpdateDate(LocalDate.now());
        exchangeRate.setPln(new BigDecimal(3.5));
        exchangeRate.setUsd(new BigDecimal(1.0));
        exchangeRate.setEur(new BigDecimal(0.9));
        exchangeRate.setGbp(new BigDecimal(0.8));
        exchangeRate.setChf(new BigDecimal(0.95));
        exchangeRate.setJpy(new BigDecimal(110.0));

        // WHEN
        ExchangeRateDTO result = exchangeRateMapper.mapToExchangeRateDTO(exchangeRate);

        // THEN
        assertNotNull(result);
        assertEquals(exchangeRate.getBaseCodeId(), result.getBaseCodeId());
        assertEquals(exchangeRate.getBaseCode().toString(), result.getBaseCode());
        assertEquals(exchangeRate.getUpdateDate(), result.getUpdateDate());
        assertEquals(exchangeRate.getPln(), result.getPln());
        assertEquals(exchangeRate.getUsd(), result.getUsd());
        assertEquals(exchangeRate.getEur(), result.getEur());
        assertEquals(exchangeRate.getGbp(), result.getGbp());
        assertEquals(exchangeRate.getChf(), result.getChf());
        assertEquals(exchangeRate.getJpy(), result.getJpy());
    }

    @Test
    void mapToExchangeRateDTOList_ShouldMapListCorrectly() {
        // GIVEN
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCodeId(1L);
        exchangeRate.setBaseCode(CurrencyType.USD);
        exchangeRate.setUpdateDate(LocalDate.now());
        exchangeRate.setPln(new BigDecimal(3.5));
        exchangeRate.setUsd(new BigDecimal(1.0));
        exchangeRate.setEur(new BigDecimal(0.9));
        exchangeRate.setGbp(new BigDecimal(0.8));
        exchangeRate.setChf(new BigDecimal(0.95));
        exchangeRate.setJpy(new BigDecimal(110.0));

        List<ExchangeRate> exchangeRates = Arrays.asList(exchangeRate);

        // WHEN
        List<ExchangeRateDTO> result = exchangeRateMapper.mapToExchangeRateDTOList(exchangeRates);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}

