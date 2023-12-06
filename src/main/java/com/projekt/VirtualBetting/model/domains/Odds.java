package com.projekt.VirtualBetting.model.domains;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ODDS")
public class Odds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ODDS_ID", nullable = false, unique = true)
    private Long oddsId;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "match_id", referencedColumnName = "MATCH_ID")
    private Match match;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal homeWin;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal draw;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal awayWin;
}
