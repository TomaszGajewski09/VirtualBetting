package com.projekt.VirtualBetting.api.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.projekt.VirtualBetting.api.service.LocalDateTimeDeserializer;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OddsAPI {

    @JsonProperty("match_id")
    private Long matchId;

    @JsonProperty("odd_bookmakers")
    private String bookmakerName;

    @JsonProperty("odd_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastUpdate;

    @JsonProperty(value = "odd_1")
    private BigDecimal homeTeamWin;

    @JsonProperty(value = "odd_x")
    private BigDecimal draw;

    @JsonProperty(value = "odd_2")
    private BigDecimal awayTeamWin;
}
