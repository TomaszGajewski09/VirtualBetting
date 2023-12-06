package com.projekt.VirtualBetting.service;


import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.model.domains.BettingOption;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.repository.BettingOptionRepository;
import com.projekt.VirtualBetting.repository.BettingSlipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BettingSlipService {
    private final BettingSlipRepository bettingSlipRepository;
    private final BettingOptionRepository bettingOptionRepository;
    private final TransactionService transactionService;

    public List<BettingSlip> findAll() {
        return bettingSlipRepository.findAll();
    }

    public BettingSlip findById(Long bettingSlipId) throws DomainNotFoundException {
        return bettingSlipRepository.findById(bettingSlipId).orElseThrow(() -> new DomainNotFoundException("BettingSlip"));
    }

    @Transactional
    public BettingSlip save(final BettingSlip bettingSlip) throws DomainNotFoundException, InsufficientBalanceException {
        BettingSlip savedBettingSlip = bettingSlipRepository.save(bettingSlip);
        transactionService.initializeTransactionsForBettingSlip(bettingSlip);

        for (BettingOption option : bettingSlip.getBettingOptions()) {
            option.setBettingSlip(savedBettingSlip);
            bettingOptionRepository.save(option);
        }

        return savedBettingSlip;
    }

    public void deleteById(Long userId) {
        bettingSlipRepository.deleteById(userId);
    }

    public void deleteAll() {
        bettingSlipRepository.deleteAll();
    }

//    public List<BettingSlip> findAllByMatch(Long matchId) {
//        // todo: pobranie wszystkich kuponów w których jest dany mecz
//        return null;
//    }
}
