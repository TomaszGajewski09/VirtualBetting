package com.projekt.VirtualBetting.validator;

import com.projekt.VirtualBetting.service.LeagueService;
import com.projekt.VirtualBetting.service.MatchService;
import com.projekt.VirtualBetting.service.OddsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.*;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class BettingSlipValidatorTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private BettingSlipValidator bettingSlipValidator;


    @Test
    void validateBettingSlip_MissingFields_ThrowsMissingFieldException() {
        // GIVEN
        BettingSlipDTO bettingSlipDTO = new BettingSlipDTO();

        // WHEN & THEN
        assertThrows(MissingFieldException.class, () -> bettingSlipValidator.validateBettingSlip(bettingSlipDTO));
    }


    @Test
    void validateMatchesExist_NonExistingMatch_ThrowsDomainNotFoundException() throws DomainNotFoundException {
        // GIVEN
        BettingSlipDTO bettingSlipDTO = new BettingSlipDTO();
        BettingOptionDTO optionDTO = new BettingOptionDTO();
        optionDTO.setMatchId(1L);
        bettingSlipDTO.setBettingOptions(Arrays.asList(optionDTO));

        when(matchService.findById(1L)).thenThrow(new DomainNotFoundException("Match"));

        // WHEN & THEN
        assertThrows(DomainNotFoundException.class, () -> bettingSlipValidator.validateMatchesExist(bettingSlipDTO));
    }

    @Test
    void validateMatchesNotFinishedAndHaveOdds_FinishedMatch_ThrowsValidationException() throws DomainNotFoundException {
        // GIVEN
        BettingSlipDTO bettingSlipDTO = new BettingSlipDTO();
        BettingOptionDTO optionDTO = new BettingOptionDTO();
        optionDTO.setMatchId(1L);
        bettingSlipDTO.setBettingOptions(Arrays.asList(optionDTO));

        Match finishedMatch = new Match();
        finishedMatch.setMatchStatus("Finished");
        when(matchService.findById(1L)).thenReturn(finishedMatch);

        // WHEN & THEN
        assertThrows(ValidationException.class, () -> bettingSlipValidator.validateMatchesNotFinishedAndHaveOdds(bettingSlipDTO));
    }

    @Test
    void validateMatchesNotFinishedAndHaveOdds_NoOdds_ThrowsValidationException() throws DomainNotFoundException {
        // GIVEN
        BettingSlipDTO bettingSlipDTO = new BettingSlipDTO();
        BettingOptionDTO optionDTO = new BettingOptionDTO();
        optionDTO.setMatchId(1L);
        bettingSlipDTO.setBettingOptions(Arrays.asList(optionDTO));

        Match matchWithoutOdds = new Match();
        matchWithoutOdds.setMatchStatus("");
        when(matchService.findById(1L)).thenReturn(matchWithoutOdds);

        // WHEN & THEN
        assertThrows(ValidationException.class, () -> bettingSlipValidator.validateMatchesNotFinishedAndHaveOdds(bettingSlipDTO));
    }

}
