package com.projekt.VirtualBetting.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Team;
import com.projekt.VirtualBetting.repository.TeamRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private LeagueService leagueService;

    @InjectMocks
    private TeamService teamService;

    @Test
    void findAll_ShouldReturnAllTeams() {
        // GIVEN
        Team team1 = new Team();
        Team team2 = new Team();
        List<Team> expectedList = Arrays.asList(team1, team2);
        when(teamRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<Team> result = teamService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamRepository).findAll();
    }

    @Test
    void findById_ShouldReturnTeam() throws DomainNotFoundException {
        // GIVEN
        Long teamId = 1L;
        Team team = new Team();
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // WHEN
        Team result = teamService.findById(teamId);

        // THEN
        assertNotNull(result);
        assertEquals(team, result);
        verify(teamRepository).findById(teamId);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            teamService.findById(teamId);
        });
    }

    @Test
    void findByLeagueId_ShouldReturnTeams() throws DomainNotFoundException {
        // GIVEN
        Long leagueId = 1L;
        League league = new League();
        Team team1 = new Team();
        Team team2 = new Team();
        List<Team> expectedList = Arrays.asList(team1, team2);
        when(leagueService.findById(leagueId)).thenReturn(league);
        when(teamRepository.findByLeague(league)).thenReturn(expectedList);

        // WHEN
        List<Team> result = teamService.findByLeagueId(leagueId);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(leagueService).findById(leagueId);
        verify(teamRepository).findByLeague(league);
    }

    @Test
    void saveOrUpdate_ShouldSaveNewTeam() {
        // GIVEN
        Team team = new Team();
        when(teamRepository.findById(team.getTeamId())).thenReturn(Optional.empty());
        when(teamRepository.save(team)).thenReturn(team);

        // WHEN
        Team result = teamService.saveOrUpdate(team);

        // THEN
        assertNotNull(result);
        verify(teamRepository).findById(team.getTeamId());
        verify(teamRepository).save(team);
    }

    @Test
    void saveOrUpdate_ShouldUpdateExistingTeam() {
        // GIVEN
        Team existingTeam = new Team();
        existingTeam.setTeamId(1L);
        Team newTeam = new Team();
        newTeam.setTeamId(1L);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(existingTeam));
        when(teamRepository.save(existingTeam)).thenReturn(existingTeam);

        // WHEN
        Team result = teamService.saveOrUpdate(newTeam);

        // THEN
        assertNotNull(result);
        verify(teamRepository).findById(1L);
        verify(teamRepository).save(existingTeam);
    }

    @Test
    void deleteAll_ShouldDeleteAllTeams() {
        // WHEN
        teamService.deleteAll();

        // THEN
        verify(teamRepository).deleteAll();
    }
}

