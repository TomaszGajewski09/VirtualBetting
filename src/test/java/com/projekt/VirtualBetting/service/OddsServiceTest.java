package com.projekt.VirtualBetting.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.repository.OddsRepository;
import com.projekt.VirtualBetting.service.MatchService;
import com.projekt.VirtualBetting.service.OddsService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OddsServiceTest {

    @Mock
    private OddsRepository oddsRepository;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private OddsService oddsService;

    @Test
    void findAllOdds_ShouldReturnAllOdds() {
        // GIVEN
        Odds odds1 = new Odds();
        Odds odds2 = new Odds();
        List<Odds> expectedList = Arrays.asList(odds1, odds2);
        when(oddsRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<Odds> result = oddsService.findAllOdds();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(oddsRepository).findAll();
    }

    @Test
    void findById_ShouldReturnOdds() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        Odds odds = new Odds();
        when(oddsRepository.findById(id)).thenReturn(Optional.of(odds));

        // WHEN
        Odds result = oddsService.findById(id);

        // THEN
        assertNotNull(result);
        assertEquals(odds, result);
        verify(oddsRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(oddsRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            oddsService.findById(id);
        });
    }

    @Test
    void saveOrUpdate_ShouldSaveNewOdds() throws DomainNotFoundException {
        // GIVEN
        Odds odds = new Odds();
        Match match = new Match();
        match.setMatchId(1L);
        odds.setMatch(match);
        when(matchService.findById(1L)).thenReturn(match);
        when(oddsRepository.findByMatch(match)).thenReturn(Optional.empty());
        when(oddsRepository.save(odds)).thenReturn(odds);

        // WHEN
        Odds result = oddsService.saveOrUpdate(odds);

        // THEN
        assertNotNull(result);
        verify(matchService).findById(1L);
        verify(oddsRepository).findByMatch(match);
        verify(oddsRepository).save(odds);
    }

    @Test
    void saveOrUpdate_ShouldUpdateExistingOdds() throws DomainNotFoundException {
        // GIVEN
        Match match = new Match();
        match.setMatchId(1L);
        Odds existingOdds = new Odds();
        existingOdds.setMatch(match);
        Odds newOdds = new Odds();
        newOdds.setMatch(match);
        when(matchService.findById(1L)).thenReturn(match);
        when(oddsRepository.findByMatch(match)).thenReturn(Optional.of(existingOdds));
        when(oddsRepository.save(any(Odds.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Odds result = oddsService.saveOrUpdate(newOdds);

        // THEN
        assertNotNull(result);
        verify(matchService).findById(1L);
        verify(oddsRepository).findByMatch(match);
        verify(oddsRepository).save(any(Odds.class));
    }

    @Test
    void deleteById_ShouldDeleteOdds() {
        // GIVEN
        Long id = 1L;

        // WHEN
        oddsService.deleteById(id);

        // THEN
        verify(oddsRepository).deleteById(id);
    }

    @Test
    void deleteAll_ShouldDeleteAllOdds() {
        // WHEN
        oddsService.deleteAll();

        // THEN
        verify(oddsRepository).deleteAll();
    }

    @Test
    void findByMatchId_ShouldReturnOdds() throws DomainNotFoundException {
        // GIVEN
        Long matchId = 1L;
        Match match = new Match();
        match.setMatchId(matchId);
        Odds expectedOdds = new Odds();
        when(matchService.findById(matchId)).thenReturn(match);
        when(oddsRepository.findByMatch(match)).thenReturn(Optional.of(expectedOdds));

        // WHEN
        Odds result = oddsService.findByMatchId(matchId);

        // THEN
        assertNotNull(result);
        assertEquals(expectedOdds, result);
        verify(matchService).findById(matchId);
        verify(oddsRepository).findByMatch(match);
    }

    @Test
    void findByMatchId_ShouldThrowDomainNotFoundException() throws DomainNotFoundException {
        // GIVEN
        Long matchId = 1L;
        Match match = new Match();
        match.setMatchId(matchId);
        when(matchService.findById(matchId)).thenReturn(match);
        when(oddsRepository.findByMatch(match)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            oddsService.findByMatchId(matchId);
        });
    }
}