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
import com.projekt.VirtualBetting.repository.LeagueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private LeagueService leagueService;

    @Test
    void findAll_ShouldReturnAllLeagues() {
        // GIVEN
        League league1 = new League();
        League league2 = new League();
        List<League> expectedList = Arrays.asList(league1, league2);
        when(leagueRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<League> result = leagueService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(leagueRepository).findAll();
    }

    @Test
    void findById_ShouldReturnLeague() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        League league = new League();
        when(leagueRepository.findById(id)).thenReturn(Optional.of(league));

        // WHEN
        League result = leagueService.findById(id);

        // THEN
        assertNotNull(result);
        assertEquals(league, result);
        verify(leagueRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(leagueRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            leagueService.findById(id);
        });
    }

    @Test
    void saveOrUpdate_ShouldSaveNewLeague() {
        // GIVEN
        League league = new League();
        when(leagueRepository.findById(league.getLeagueId())).thenReturn(Optional.empty());
        when(leagueRepository.save(league)).thenReturn(league);

        // WHEN
        League result = leagueService.saveOrUpdate(league);

        // THEN
        assertNotNull(result);
        verify(leagueRepository).findById(league.getLeagueId());
        verify(leagueRepository).save(league);
    }

    @Test
    void saveOrUpdate_ShouldUpdateExistingLeague() {
        // GIVEN
        League existingLeague = new League();
        existingLeague.setLeagueId(1L);
        League newLeague = new League();
        newLeague.setLeagueId(1L);
        newLeague.setLeagueName("New League Name");
        newLeague.setCountryName("New Country Name");
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(existingLeague));
        when(leagueRepository.save(any(League.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        League result = leagueService.saveOrUpdate(newLeague);

        // THEN
        assertNotNull(result);
        assertEquals("New League Name", result.getLeagueName());
        assertEquals("New Country Name", result.getCountryName());
        verify(leagueRepository).findById(1L);
        verify(leagueRepository).save(any(League.class));
    }

    @Test
    void delete_ShouldDeleteLeague() {
        // GIVEN
        Long id = 1L;

        // WHEN
        leagueService.delete(id);

        // THEN
        verify(leagueRepository).deleteById(id);
    }

    @Test
    void deleteAll_ShouldDeleteAllLeagues() {
        // WHEN
        leagueService.deleteAll();

        // THEN
        verify(leagueRepository).deleteAll();
    }
}
