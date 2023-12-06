package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.BettingOption;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.dto.BettingOptionDTO;
import com.projekt.VirtualBetting.model.dto.BettingSlipDTO;
import com.projekt.VirtualBetting.model.types.BettingSlipStatus;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.SelectedOption;
import com.projekt.VirtualBetting.service.MatchService;
import com.projekt.VirtualBetting.service.TransactionService;
import com.projekt.VirtualBetting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BettingSlipMapper {

    private final UserService userService;
    private final TransactionService transactionService;
    private final MatchService matchService;
    private final BettingOptionMapper bettingOptionMapper;

    public BettingSlip mapToBettingSlip(BettingSlipDTO bettingSlipDTO) throws DomainNotFoundException {

        Long userId = bettingSlipDTO.getUserId();

        List<Transaction> transactions = new ArrayList<>();
        if (bettingSlipDTO.getBettingSlipId() != null && !bettingSlipDTO.getTransactionsIds().isEmpty()) {
            for (Long id : bettingSlipDTO.getTransactionsIds()) {
                transactions.add(transactionService.findById(id));
            }
        } else {
            transactions = Collections.emptyList();
        }

        List<BettingOption> bettingOptions = new ArrayList<>();
        if (!bettingSlipDTO.getBettingOptions().isEmpty()) {
            for (BettingOptionDTO optionDTO : bettingSlipDTO.getBettingOptions()) {
                Match match = matchService.findById(optionDTO.getMatchId());
                SelectedOption selectedOption = SelectedOption.valueOf(optionDTO.getSelectedOption());

                BettingOption bettingOption = new BettingOption();
                bettingOption.setMatch(match);
                bettingOption.setSelectedOption(selectedOption);
                bettingOptions.add(bettingOption);
            }
        } else {
            bettingOptions = Collections.emptyList();
        }

        return BettingSlip.builder()
                .bettingSlipId(bettingSlipDTO.getBettingSlipId())
                .user((userId != null)? userService.findUserById(userId) : null)
                .status(BettingSlipStatus.getIfValid(bettingSlipDTO.getStatus()))
                .currency(CurrencyType.getIfValid(bettingSlipDTO.getCurrency()))
                .wageredAmount(bettingSlipDTO.getWageredAmount())
                .possiblePayoutAmount(bettingSlipDTO.getPossiblePayoutAmount())
                .transactions(transactions)
                .bettingOptions(bettingOptions)
                .build();
    }



    public BettingSlipDTO mapToBettingSlipDTO(BettingSlip bettingSlip) {

        return BettingSlipDTO.builder()
                .bettingSlipId(bettingSlip.getBettingSlipId())
                .userId((bettingSlip.getUser() != null)? bettingSlip.getUser().getUserid() : null)
                .status(bettingSlip.getStatus().toString())
                .currency(bettingSlip.getCurrency().toString())
                .wageredAmount(bettingSlip.getWageredAmount())
                .possiblePayoutAmount(bettingSlip.getPossiblePayoutAmount())
                .transactionsIds(
                        bettingSlip.getTransactions().stream()
                                .map(t -> t.getTransactionId())
                                .collect(Collectors.toList())
                )
                .bettingOptions(bettingOptionMapper.mapToBettingOptionDTOList(bettingSlip.getBettingOptions()))
                .build();
    }

    public List<BettingSlipDTO> mapToBettingSlipDTOList(List<BettingSlip> bettingSlips) {
        return bettingSlips.stream()
                .map(this::mapToBettingSlipDTO)
                .collect(Collectors.toList());
    }
}
