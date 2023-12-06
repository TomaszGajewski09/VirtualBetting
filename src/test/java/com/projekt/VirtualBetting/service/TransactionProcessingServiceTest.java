package com.projekt.VirtualBetting.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import com.projekt.VirtualBetting.service.CurrencyConversionService;
import com.projekt.VirtualBetting.service.WalletService;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransactionProcessingServiceTest {

    @Mock
    private CurrencyConversionService currencyConversionService;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private TransactionProcessingService transactionProcessingService;

    @Test
    void processTransaction_WithCurrencyConversion() throws DomainNotFoundException, InsufficientBalanceException {
        // GIVEN
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(200));
        wallet.setCurrency(CurrencyType.PLN);
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(50));
        transaction.setCurrency(CurrencyType.USD);
        transaction.setType(TransactionType.WITHDRAWAL);

        when(currencyConversionService.convertCurrency(any(), any(), any())).thenReturn(BigDecimal.valueOf(200));

        // WHEN
        Transaction result = transactionProcessingService.processTransaction(transaction);

        // THEN
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200), result.getAmount());
        verify(currencyConversionService).convertCurrency(BigDecimal.valueOf(50), CurrencyType.USD, CurrencyType.PLN);
    }

    @Test
    void processTransaction_WithSufficientBalance() throws DomainNotFoundException, InsufficientBalanceException {
        // GIVEN
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(200));
        wallet.setCurrency(CurrencyType.PLN);
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(50));
        transaction.setCurrency(CurrencyType.PLN);
        transaction.setType(TransactionType.WITHDRAWAL);

        // WHEN
        Transaction result = transactionProcessingService.processTransaction(transaction);

        // THEN
        assertNotNull(result);
        verify(currencyConversionService, never()).convertCurrency(any(), any(), any());
    }

    @Test
    void processTransaction_InsufficientBalance() {
        // GIVEN
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(30));
        wallet.setCurrency(CurrencyType.PLN);
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(50));
        transaction.setCurrency(CurrencyType.PLN);
        transaction.setType(TransactionType.WITHDRAWAL);

        // THEN
        assertThrows(InsufficientBalanceException.class, () -> {
            // WHEN
            transactionProcessingService.processTransaction(transaction);
        });
    }
}

