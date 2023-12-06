package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Odds;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.model.types.SelectedOption;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BettingCalculationService {

    private final OddsService oddsService;

    public BettingCalculationService(OddsService oddsService) {
        this.oddsService = oddsService;
    }

    public BigDecimal calculatePotentialWin(BettingSlipDTO bettingSlipDTO) throws DomainNotFoundException {
        BigDecimal totalOdds = bettingSlipDTO.getWageredAmount();
        for (BettingOptionDTO bettingOptionDTO : bettingSlipDTO.getBettingOptions()) {
            Odds odds = oddsService.findByMatchId(bettingOptionDTO.getMatchId());
            BigDecimal matchOdd = getOddsForMatch(odds, bettingOptionDTO);
            totalOdds = totalOdds.multiply(matchOdd);
        }
        return totalOdds;
    }

    private BigDecimal getOddsForMatch(Odds odds, BettingOptionDTO bettingOptionDTO) {
        if(bettingOptionDTO.getSelectedOption().equals(SelectedOption.HOME.name())) return odds.getHomeWin();
        if(bettingOptionDTO.getSelectedOption().equals(SelectedOption.AWAY.name())) return odds.getAwayWin();
        if(bettingOptionDTO.getSelectedOption().equals(SelectedOption.DRAW.name())) return odds.getDraw();
        return BigDecimal.ONE;
    }
}

