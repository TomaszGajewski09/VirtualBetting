package com.projekt.VirtualBetting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchUpdateService {
/*

    private final MatchService matchService;
    private final BettingSlipService bettingSlipService;

    public void updateMatchesAndBettingSlips() {
        List<Match> updatedMatches = matchService.findAll(); // todo: dodać nową metode żeby nie pobierało starych meczy

        for (Match match : updatedMatches) {
            if (matchHasChanged(match)) {
                updateRelatedBettingSlips(match);
            }
        }
    }

    private boolean matchHasChanged(Match match) {
        // todo: logika określająca, czy mecz został zaktualizowany (np. zmiana wyniku, statusu)
        return true;
    }

    private void updateRelatedBettingSlips(Match match) throws InsufficientBalanceException, DomainNotFoundException {
        List<BettingSlip> bettingSlips = bettingSlipService.findAllByMatch(match.getMatchId());

        for (BettingSlip slip : bettingSlips) {
            updateBettingSlipStatus(slip, match);

            bettingSlipService.saveOrUpdate(slip);
        }
    }

    private void updateBettingSlipStatus(BettingSlip slip, Match match) {
        // todo: Logika określająca, jak zmienić status zakładu bukmacherskiego
        // na podstawie zmian w meczu (np. wygrana, przegrana, odwołanie meczu)
//        slip.setStatus(BettingSlipStatus.WIN);
    }

    // Dodatkowe metody, np. obsługa sytuacji wyjątkowych
*/
}
