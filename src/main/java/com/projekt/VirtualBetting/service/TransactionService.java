package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.TransactionDTO;
import com.projekt.VirtualBetting.model.types.BettingSlipStatus;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import com.projekt.VirtualBetting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final EntityUpdates entityUpdates;
    private final TransactionProcessingService transactionProcessingService;

    public void initializeTransactionsForBettingSlip(BettingSlip bettingSlip) throws DomainNotFoundException, InsufficientBalanceException {
        if (bettingSlip.getStatus() != BettingSlipStatus.LOSE) {
            TransactionType type = null;
            BigDecimal amount = null;
            String description = "";

            if (bettingSlip.getStatus() == BettingSlipStatus.PAID) {
                type = TransactionType.BET_PLACEMENT;
                amount = bettingSlip.getWageredAmount().negate(); // using negate for -1 multiplication
                description = "Paying for the bet";
            } else if (bettingSlip.getStatus() == BettingSlipStatus.WIN) {
                type = TransactionType.WIN;
                amount = bettingSlip.getPossiblePayoutAmount();
                description = "Winning the bet";
            }

            Transaction transaction = new Transaction();
            transaction.setBettingSlip(bettingSlip);
            transaction.setWallet(bettingSlip.getUser().getWallet());
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setCurrency(bettingSlip.getCurrency());
            transaction.setDescription(description);

            save(transaction);
        }
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllByWallet(Long walletId) throws DomainNotFoundException {
        Wallet wallet = walletService.findById(walletId);
        return transactionRepository.findAllByWallet(wallet);
    }

    public Transaction findById(Long id) throws DomainNotFoundException {
        return transactionRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Transaction"));
    }

    public Transaction save(Transaction transaction) throws DomainNotFoundException, InsufficientBalanceException {
        Transaction savedTransaction = transactionRepository.save(transactionProcessingService.processTransaction(transaction));

     /* todo: Po stworzeniu transakcji ma
         - sprawdzanie currency - jeśli jest inna waluta trzeba przewalutować
     */
        entityUpdates.walletUpdate(transaction);

        return savedTransaction;

    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
}

