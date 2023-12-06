package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.model.types.SelectedOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.projekt.VirtualBetting.model.domains.Odds;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BettingCalculationServiceTest {

    @Mock
    private OddsService oddsService;

    @InjectMocks
    private BettingCalculationService bettingCalculationService;



    @Test
    void calculatePotentialWin_Success() throws DomainNotFoundException {
        // Arrange
        BigDecimal wageredAmount = new BigDecimal("100");
        BigDecimal homeWinOdds = new BigDecimal("1.5");
        Long matchId = 1L;

        BettingOptionDTO option = new BettingOptionDTO(matchId, SelectedOption.HOME.name());
        BettingSlipDTO slipDTO = new BettingSlipDTO();
        slipDTO.setWageredAmount(wageredAmount);
        slipDTO.setBettingOptions(List.of(option));

        Odds odds = new Odds();
        odds.setHomeWin(homeWinOdds);

        when(oddsService.findByMatchId(matchId)).thenReturn(odds);

        // Act
        BigDecimal potentialWin = bettingCalculationService.calculatePotentialWin(slipDTO);

        // Assert
        assertEquals(wageredAmount.multiply(homeWinOdds), potentialWin);
    }

    @Test
    void calculatePotentialWin_DomainNotFoundException() throws DomainNotFoundException {
        // Arrange
        Long invalidMatchId = 2L;
        BettingOptionDTO option = new BettingOptionDTO(invalidMatchId, SelectedOption.HOME.name());
        BettingSlipDTO slipDTO = new BettingSlipDTO();
        slipDTO.setWageredAmount(new BigDecimal("100"));
        slipDTO.setBettingOptions(List.of(option));

        when(oddsService.findByMatchId(invalidMatchId)).thenThrow(new DomainNotFoundException("Match not found"));

        // Act & Assert
        assertThrows(DomainNotFoundException.class, () -> bettingCalculationService.calculatePotentialWin(slipDTO));
    }

    // Additional tests for different selected options and edge cases
}

