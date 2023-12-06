package com.projekt.VirtualBetting.model.domains;

import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "BETTING_SLIP_ID")
    private BettingSlip bettingSlip;

    @ManyToOne
    @JoinColumn(name = "WALLET_ID", nullable = false)
    private Wallet wallet;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal beforeTransaction;

    @Column(updatable = false, nullable = false)
    private LocalDateTime transactionDateTime;

    @Column(length = 255)
    private String description;

    @PrePersist
    protected void prePersist() {
        if (transactionDateTime == null) {
            transactionDateTime = LocalDateTime.now();
        }
        if (wallet != null && beforeTransaction == null) {
            beforeTransaction = wallet.getBalance();
        }
    }

}
