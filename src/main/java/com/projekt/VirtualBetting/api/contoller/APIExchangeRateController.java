package com.projekt.VirtualBetting.api.contoller;


import com.projekt.VirtualBetting.api.facade.APIExchangeRateFacade;
import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class APIExchangeRateController {
    private final APIExchangeRateFacade exchangeRateFacade;

    @GetMapping(value = "/{currencyCode}")
    public ExchangeRate getExchangeRate(@PathVariable String currencyCode) {
        return exchangeRateFacade.fetchExchangeRate(currencyCode);
    }
}
