package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.dto.ExchangeRateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExchangeRateMapper {

    public ExchangeRateDTO mapToExchangeRateDTO(ExchangeRate exchangeRate) {
        return ExchangeRateDTO.builder()
                .baseCodeId(exchangeRate.getBaseCodeId())
                .baseCode(exchangeRate.getBaseCode().toString())
                .updateDate(exchangeRate.getUpdateDate())
                .pln(exchangeRate.getPln())
                .usd(exchangeRate.getUsd())
                .eur(exchangeRate.getEur())
                .gbp(exchangeRate.getGbp())
                .chf(exchangeRate.getChf())
                .jpy(exchangeRate.getJpy())
                .build();
    }

    public List<ExchangeRateDTO> mapToExchangeRateDTOList(List<ExchangeRate> exchangeRateList) {
        return exchangeRateList.stream()
                .map(this::mapToExchangeRateDTO)
                .collect(Collectors.toList());
    }
}
