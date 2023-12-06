package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Test
    void findActualByBaseCode_ShouldReturnExchangeRate() throws DomainNotFoundException {
        // GIVEN
        String baseCode = "USD";
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCode(CurrencyType.USD);
        when(exchangeRateRepository.findByBaseCodeAndUpdateDate(CurrencyType.USD, LocalDate.now()))
                .thenReturn(Optional.of(exchangeRate));

        // WHEN
        ExchangeRate result = exchangeRateService.findActualByBaseCode(baseCode);

        // THEN
        assertNotNull(result);
        assertEquals(CurrencyType.USD, result.getBaseCode());
    }

    @Test
    void findActualByBaseCode_ThrowsException_IfNotFound() {
        // GIVEN
        String baseCode = "USD";
        when(exchangeRateRepository.findByBaseCodeAndUpdateDate(CurrencyType.USD, LocalDate.now()))
                .thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            exchangeRateService.findActualByBaseCode(baseCode);
        });
    }

    @Test
    void save_ShouldSaveNewExchangeRate() {
        // GIVEN
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCode(CurrencyType.EUR);
        exchangeRate.setPln(BigDecimal.valueOf(4.50));
        when(exchangeRateRepository.findByBaseCodeAndUpdateDate(CurrencyType.EUR, LocalDate.now()))
                .thenReturn(Optional.empty());
        when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(exchangeRate);

        // WHEN
        ExchangeRate savedExchangeRate = exchangeRateService.save(exchangeRate);

        // THEN
        verify(exchangeRateRepository).save(exchangeRate);
        assertEquals(CurrencyType.EUR, savedExchangeRate.getBaseCode());
    }

    @Test
    void findAll_ShouldReturnListOfExchangeRates() {
        // GIVEN
        ExchangeRate exchangeRate1 = new ExchangeRate();
        ExchangeRate exchangeRate2 = new ExchangeRate();
        List<ExchangeRate> expectedList = Arrays.asList(exchangeRate1, exchangeRate2);
        when(exchangeRateRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<ExchangeRate> result = exchangeRateService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(exchangeRateRepository).findAll();
    }

    @Test
    void findById_ShouldReturnExchangeRate() {
        // GIVEN
        Long id = 1L;
        ExchangeRate exchangeRate = new ExchangeRate();
        when(exchangeRateRepository.findById(id)).thenReturn(Optional.of(exchangeRate));

        // WHEN
        ExchangeRate result = exchangeRateService.findById(id);

        // THEN
        assertNotNull(result);
        verify(exchangeRateRepository).findById(id);
    }

    @Test
    void findByBaseCodeAndActualDate_ShouldReturnListOfExchangeRates() {
        // GIVEN
        String baseCode = "USD";
        ExchangeRate exchangeRate1 = new ExchangeRate();
        ExchangeRate exchangeRate2 = new ExchangeRate();
        List<ExchangeRate> expectedList = Arrays.asList(exchangeRate1, exchangeRate2);
        when(exchangeRateRepository.findByBaseCode(CurrencyType.USD)).thenReturn(expectedList);

        // WHEN
        List<ExchangeRate> result = exchangeRateService.findByBaseCodeAndActualDate(baseCode);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(exchangeRateRepository).findByBaseCode(CurrencyType.USD);
    }

    @Test
    void findByBaseCodeAndActualDate_ShouldThrowInvalidEnumTypeException_IfInvalidBaseCode() {
        // GIVEN
        String baseCode = "INVALID_CODE";

        // THEN
        assertThrows(InvalidEnumTypeException.class, () -> {
            // WHEN
            exchangeRateService.findByBaseCodeAndActualDate(baseCode);
        });
    }

    @Test
    void deleteById_ShouldDeleteExchangeRate() {
        // GIVEN
        Long id = 1L;

        // WHEN
        exchangeRateService.deleteById(id);

        // THEN
        verify(exchangeRateRepository).deleteById(id);
    }

    @Test
    void deleteAll_ShouldDeleteAllExchangeRates() {
        // GIVEN

        // WHEN
        exchangeRateService.deleteAll();

        // THEN
        verify(exchangeRateRepository).deleteAll();
    }
}

