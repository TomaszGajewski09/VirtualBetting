package com.projekt.VirtualBetting.model.domains;

import com.projekt.VirtualBetting.model.types.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ExchangeRate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXCHANGE_RATE_ID", nullable = false, unique = true)
    private Long baseCodeId;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyType baseCode;

    @Column(nullable = false)
    private LocalDate updateDate;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal pln;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal usd;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal eur;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal gbp;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal chf;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal jpy;

    @PrePersist
    @PreUpdate
    protected void updateTimestamp() {
        updateDate = LocalDate.now();
    }
}