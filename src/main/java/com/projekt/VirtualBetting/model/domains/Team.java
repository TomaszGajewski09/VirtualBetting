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
@Table(name = "TEAMS")
public class Team {
    @Id
    @Column(name = "TEAM_ID", nullable = false, unique = true)
    private Long teamId;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;

    @OneToMany(mappedBy = "homeTeam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Match> homeMatches = new ArrayList<>();

    @OneToMany(mappedBy = "awayTeam", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Match> awayMatches = new ArrayList<>();
}
