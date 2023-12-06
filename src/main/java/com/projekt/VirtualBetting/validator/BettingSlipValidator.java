package com.projekt.VirtualBetting.validator;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.MissingFieldException;
import com.projekt.VirtualBetting.exception.ValidationException;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.model.types.SelectedOption;
import com.projekt.VirtualBetting.service.LeagueService;
import com.projekt.VirtualBetting.service.MatchService;
import com.projekt.VirtualBetting.service.OddsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BettingSlipValidator {

    private final MatchService matchService;

    public void validateBettingSlip(BettingSlipDTO bettingSlipDTO) throws ValidationException, MissingFieldException, DomainNotFoundException {
        validateNecessaryFields(bettingSlipDTO);
        validateMatchesExist(bettingSlipDTO);
        validateMatchesNotFinishedAndHaveOdds(bettingSlipDTO);
        validateMatchesWithinOneWeek(bettingSlipDTO);
        validateMatchesFromSameLeague(bettingSlipDTO);
    }

    private void validateNecessaryFields(BettingSlipDTO bettingSlipDTO) throws MissingFieldException {
        if(bettingSlipDTO.getUserId() == null) throw new MissingFieldException("userId");;
        if (bettingSlipDTO.getStatus() == null) throw new MissingFieldException("status");;
        if (bettingSlipDTO.getCurrency() == null) throw new MissingFieldException("currency");;
        if (bettingSlipDTO.getWageredAmount() == null) throw new MissingFieldException("wageredAmount");;
//        if (bettingSlipDTO.getPossiblePayoutAmount() == null) throw new MissingFieldException("possiblePayoutAmount");;
        if (bettingSlipDTO.getBettingOptions() == null || bettingSlipDTO.getBettingOptions().isEmpty()) throw new MissingFieldException("matchIds");
        for (BettingOptionDTO bettingOptionDTO : bettingSlipDTO.getBettingOptions()) {
            if (bettingOptionDTO.getMatchId() == null) throw new MissingFieldException("bettingOptions: { \"matchId\": }");
            if (bettingOptionDTO.getSelectedOption() == null) throw new MissingFieldException("bettingOptions: { \"selectedOption\": }");
            SelectedOption.getIfValid(bettingOptionDTO.getSelectedOption());
        }

    }

    public void validateMatchesExist(BettingSlipDTO bettingSlipDTO) throws DomainNotFoundException {
        List<BettingOptionDTO> bettingOptionDTOList = bettingSlipDTO.getBettingOptions();
        for (BettingOptionDTO bettingOptionDTO : bettingOptionDTOList) {
            matchService.findById(bettingOptionDTO.getMatchId());
        }
    }

    void validateMatchesNotFinishedAndHaveOdds(BettingSlipDTO bettingSlipDTO) throws DomainNotFoundException, ValidationException {
        List<BettingOptionDTO> bettingOptionDTOList = bettingSlipDTO.getBettingOptions();
        for (BettingOptionDTO bettingOptionDTO : bettingOptionDTOList) {
            Match match = matchService.findById(bettingOptionDTO.getMatchId());
            if (!match.getMatchStatus().equals("")) {
                throw new ValidationException("Mecz już się odbył lub aktualnie się nie odbędzie: " + bettingOptionDTO.getMatchId());
            }
            if (match.getOdds() == null) {
                throw new ValidationException("Brak kursów meczu dla: " + bettingOptionDTO.getMatchId());
            }

        }
    }

    void validateMatchesWithinOneWeek(BettingSlipDTO bettingSlipDTO) throws ValidationException, DomainNotFoundException {
        List<BettingOptionDTO> bettingOptionDTOList = bettingSlipDTO.getBettingOptions();

        List<LocalDate> dateList = new ArrayList<>();
        for (BettingOptionDTO bettingOptionDTO : bettingOptionDTOList) {
                Match match = matchService.findById(bettingOptionDTO.getMatchId());
                if (match.getDate() != null && !match.getDate().isBefore(LocalDate.now())) {
                    dateList.add(match.getDate());
                } else {
                    throw new ValidationException("Niepoprawna data meczu: " + bettingOptionDTO.getMatchId());
                }

        }

        if (!dateList.isEmpty()) {
            Collections.sort(dateList);
            LocalDate earliestDate = dateList.get(0);
            LocalDate latestDate = dateList.get(dateList.size() - 1);
            long daysBetween = ChronoUnit.DAYS.between(earliestDate, latestDate);

            if (daysBetween > 7) {
                throw new ValidationException("Różnica czasowa między meczami przekracza tydzień");
            }
        }
    }

    private void validateMatchesFromSameLeague(BettingSlipDTO bettingSlipDTO) throws ValidationException, DomainNotFoundException {
        List<BettingOptionDTO> bettingOptionDTOList = bettingSlipDTO.getBettingOptions();
        Long leagueId = matchService.findById(bettingSlipDTO.getBettingOptions().get(0).getMatchId()).getLeague().getLeagueId();
        for (BettingOptionDTO bettingOptionDTO : bettingOptionDTOList) {
            if (!leagueId.equals(matchService.findById(bettingOptionDTO.getMatchId()).getLeague().getLeagueId())) {
                throw new ValidationException("Mecze są z różnych lig");
            }
        }
    }
}

