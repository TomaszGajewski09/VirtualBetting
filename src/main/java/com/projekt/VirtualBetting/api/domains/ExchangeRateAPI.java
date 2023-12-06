package com.projekt.VirtualBetting.api.domains;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateAPI {
    @JsonProperty("base_code")
    private String baseCode;

    private BigDecimal pln;
    private BigDecimal usd;
    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal chf;
    private BigDecimal jpy;

    @JsonProperty("conversion_rates")
    private void unpackNested(Map<String, Double> conversionRates) {
        this.pln = BigDecimal.valueOf(conversionRates.get("PLN"));
        this.usd = BigDecimal.valueOf(conversionRates.get("USD"));
        this.eur = BigDecimal.valueOf(conversionRates.get("EUR"));
        this.gbp = BigDecimal.valueOf(conversionRates.get("GBP"));
        this.chf = BigDecimal.valueOf(conversionRates.get("CHF"));
        this.jpy = BigDecimal.valueOf(conversionRates.get("JPY"));
    }

}
