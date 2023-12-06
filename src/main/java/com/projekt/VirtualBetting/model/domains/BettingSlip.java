package com.projekt.VirtualBetting.model.domains;

import com.projekt.VirtualBetting.model.types.BettingSlipStatus;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BETTING_SLIP")
public class BettingSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BETTING_SLIP_ID", unique = true, nullable = false)
    private Long bettingSlipId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BettingSlipStatus status;

    @Column(nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal wageredAmount;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal possiblePayoutAmount;

    @Column()
    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "bettingSlip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    @Column()
    @OneToMany(
            targetEntity = BettingOption.class,
            mappedBy = "bettingSlip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<BettingOption> bettingOptions = new ArrayList<>();

}
