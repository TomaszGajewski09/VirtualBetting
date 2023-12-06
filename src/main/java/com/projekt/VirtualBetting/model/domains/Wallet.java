package com.projekt.VirtualBetting.model.domains;

import com.projekt.VirtualBetting.model.types.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WALLETS")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WALLET_ID", unique = true)
    private Long walletId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "USER_ID")
    private User user;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(nullable = false)
    private LocalDateTime lastTransactionTime;

    @Column()
    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "wallet",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Transaction> transaction = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (currency == null) {
            currency = CurrencyType.PLN;
        }
        lastTransactionTime = LocalDateTime.now();


    }
}
