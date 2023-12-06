package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.ExchangeRateMapper;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.dto.ExchangeRateDTO;
import com.projekt.VirtualBetting.service.ExchangeRateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {
    ExchangeRateService exchangeRateService;
    ExchangeRateMapper exchangeRateMapper;

    @GetMapping
    public List<ExchangeRateDTO> getExchangeRates() {
        List<ExchangeRate> exchangeRateList = exchangeRateService.findAll();
        return exchangeRateMapper.mapToExchangeRateDTOList(exchangeRateList);
    }

    @GetMapping("/actual/{currency}")
    public ExchangeRateDTO getActualExchangeRate(@PathVariable String currency) throws DomainNotFoundException {
        ExchangeRate exchangeRateList = exchangeRateService.findActualByBaseCode(currency);
        return exchangeRateMapper.mapToExchangeRateDTO(exchangeRateList);
    }

    @GetMapping("/{currency}")
    public List<ExchangeRateDTO> getExchangeRatesByCurrency(@PathVariable String currency) {
        List<ExchangeRate> exchangeRateList = exchangeRateService.findByBaseCodeAndActualDate(currency);
        return exchangeRateMapper.mapToExchangeRateDTOList(exchangeRateList);
    }
}
