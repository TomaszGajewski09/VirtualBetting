package com.projekt.VirtualBetting.model.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Leagues")
public class League {

    @Id
    @Column(name = "LEAGUE_ID", nullable = false, unique = true)
    private Long leagueId;

    @Column(nullable = false, length = 255)
    private String leagueName;

    @Column(nullable = false, length = 255)
    private String countryName;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Team> teams = new ArrayList<>();
}