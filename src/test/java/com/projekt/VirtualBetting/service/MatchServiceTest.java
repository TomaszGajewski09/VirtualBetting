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
import com.projekt.VirtualBetting.repository.MatchRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void findAll_ShouldReturnAllMatches() {
        // GIVEN
        Match match1 = new Match();
        Match match2 = new Match();
        List<Match> expectedList = Arrays.asList(match1, match2);
        when(matchRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<Match> result = matchService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(matchRepository).findAll();
    }

    @Test
    void findById_ShouldReturnMatch() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        Match match = new Match();
        when(matchRepository.findById(id)).thenReturn(Optional.of(match));

        // WHEN
        Match result = matchService.findById(id);

        // THEN
        assertNotNull(result);
        assertEquals(match, result);
        verify(matchRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(matchRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            matchService.findById(id);
        });
    }

    @Test
    void saveOrUpdate_ShouldSaveNewMatch() {
        // GIVEN
        Match match = new Match();
        when(matchRepository.findById(match.getMatchId())).thenReturn(Optional.empty());
        when(matchRepository.save(match)).thenReturn(match);

        // WHEN
        Match result = matchService.saveOrUpdate(match);

        // THEN
        assertNotNull(result);
        verify(matchRepository).findById(match.getMatchId());
        verify(matchRepository).save(match);
    }

    @Test
    void saveOrUpdate_ShouldUpdateExistingMatch() {
        // GIVEN
        Match existingMatch = new Match();
        existingMatch.setMatchId(1L);
        Match newMatch = new Match();
        newMatch.setMatchId(1L);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(existingMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Match result = matchService.saveOrUpdate(newMatch);

        // THEN
        assertNotNull(result);
        verify(matchRepository).findById(1L);
        verify(matchRepository).save(any(Match.class));
    }

    @Test
    void deleteById_ShouldDeleteMatch() {
        // GIVEN
        Long id = 1L;

        // WHEN
        matchService.deleteById(id);

        // THEN
        verify(matchRepository).deleteById(id);
    }

    @Test
    void deleteAll_ShouldDeleteAllMatches() {
        // WHEN
        matchService.deleteAll();

        // THEN
        verify(matchRepository).deleteAll();
    }
}
