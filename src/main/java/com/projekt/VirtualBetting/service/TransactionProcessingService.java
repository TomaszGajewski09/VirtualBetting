package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.types.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionProcessingService {

    private final CurrencyConversionService currencyConversionService;



    //todo: do modyfikacji
    public Transaction processTransaction(Transaction transaction) throws DomainNotFoundException, InsufficientBalanceException {
        Wallet userWallet = transaction.getWallet();
        BigDecimal amount = transaction.getAmount();


        if (!transaction.getCurrency().equals(userWallet.getCurrency())) {
            amount = currencyConversionService.convertCurrency(amount, transaction.getCurrency(), userWallet.getCurrency());
            transaction.setAmount(amount);
        }


        if (TransactionType.requiresDeduction(transaction.getType())) {
            checkUserBalance(userWallet, amount);
        }

        return transaction;
    }

    private void checkUserBalance(Wallet wallet, BigDecimal amount) throws InsufficientBalanceException {
        if(amount.compareTo(BigDecimal.ZERO) < 0) amount = amount.multiply(new BigDecimal(-1));
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("User does not have sufficient balance.");
        }
    }
}

