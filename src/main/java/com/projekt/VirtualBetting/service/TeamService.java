package com.projekt.VirtualBetting.service;


import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueService leagueService;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long teamId) throws DomainNotFoundException {
        return teamRepository.findById(teamId).orElseThrow(() -> new DomainNotFoundException("Team"));
    }

    public List<Team> findByLeagueId(Long leagueId) throws DomainNotFoundException {
        League league = leagueService.findById(leagueId);
        return teamRepository.findByLeague(league);
    }

    @Transactional
    public Team saveOrUpdate(final Team team) {
        return teamRepository.findById(team.getTeamId())
                .map(existingTeam -> updateTeam(existingTeam, team))
                .orElseGet(() -> teamRepository.save(team));
    }

    private Team updateTeam(Team existingTeam, Team newTeam) {
        existingTeam.setName(newTeam.getName());
        existingTeam.setLeague(newTeam.getLeague());
        return teamRepository.save(existingTeam);
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }
}
