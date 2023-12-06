package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateService.class);

    public List<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate findById(Long id) {
        return exchangeRateRepository.findById(id).orElseThrow(null);
    }

    public ExchangeRate findActualByBaseCode(String baseCode) throws DomainNotFoundException {
        if (!CurrencyType.isValid(baseCode)) {
            throw new InvalidEnumTypeException("CurrencyType: " + baseCode);
        }
        return exchangeRateRepository.findByBaseCodeAndUpdateDate(CurrencyType.getIfValid(baseCode), LocalDate.now())
                .orElseThrow(() -> new DomainNotFoundException("ExchangeRate"));
    }

    public List<ExchangeRate> findByBaseCodeAndActualDate(String baseCode) {
        if (!CurrencyType.isValid(baseCode)) {
            throw new InvalidEnumTypeException("CurrencyType: " + baseCode);
        }
        return exchangeRateRepository.findByBaseCode(CurrencyType.getIfValid(baseCode));
    }

    public ExchangeRate save(final ExchangeRate exchangeRate) {
        Optional<ExchangeRate> existingExchangeRate = exchangeRateRepository.findByBaseCodeAndUpdateDate(exchangeRate.getBaseCode(), LocalDate.now());

        if (existingExchangeRate.isPresent()) {
            LOGGER.warn("Entity already exist - " + exchangeRate);
            return exchangeRate;
        } else {
            return exchangeRateRepository.save(exchangeRate);
        }
    }


    public void deleteById(Long id) {
        exchangeRateRepository.deleteById(id);
    }

    public void deleteAll() {
        exchangeRateRepository.deleteAll();
    }
}
