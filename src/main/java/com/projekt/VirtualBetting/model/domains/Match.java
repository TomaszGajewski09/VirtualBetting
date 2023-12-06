package com.projekt.VirtualBetting.model.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MATCHES")
public class Match {

    @Id
    @Column(name = "MATCH_ID", unique = true, nullable = false)
    private Long matchId;

    @Column
    private LocalDateTime lastTimeUpdate;

    @Column(nullable = false, length = 50)
    private String matchStatus;

    @Column(nullable = false)
    private int homeTeamScore;

    @Column(nullable = false)
    private int awayTeamScore;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HomeTeamID", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AwayTeamID", nullable = false)
    private Team awayTeam;

    @OneToOne(mappedBy = "match",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Odds odds;

    @Column()
    @OneToMany(
            targetEntity = BettingOption.class,
            mappedBy = "match",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<BettingOption> bettingOptions = new ArrayList<>();


    @PrePersist
    @PreUpdate
    protected void updateTimestamp() {
        lastTimeUpdate = LocalDateTime.now();
    }
}
