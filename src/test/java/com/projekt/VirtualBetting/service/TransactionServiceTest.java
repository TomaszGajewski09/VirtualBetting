package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.model.domains.User;
import com.projekt.VirtualBetting.model.dto.TransactionDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.types.BettingSlipStatus;
import com.projekt.VirtualBetting.repository.TransactionRepository;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private EntityUpdates entityUpdates;

    @Mock
    private TransactionProcessingService transactionProcessingService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void initializeTransactionsForBettingSlip_ShouldCreateTransaction() throws DomainNotFoundException, InsufficientBalanceException {
        // GIVEN
        BettingSlip bettingSlip = new BettingSlip();
        bettingSlip.setStatus(BettingSlipStatus.PAID);
        bettingSlip.setWageredAmount(new BigDecimal("100"));
        bettingSlip.setPossiblePayoutAmount(new BigDecimal("200"));
        bettingSlip.setCurrency(CurrencyType.PLN);
        Wallet wallet = new Wallet();
        bettingSlip.setUser(new User());
        bettingSlip.getUser().setWallet(wallet);

        // WHEN
        transactionService.initializeTransactionsForBettingSlip(bettingSlip);

        // THEN
        verify(transactionProcessingService).processTransaction(any(Transaction.class));
        verify(entityUpdates).walletUpdate(any(Transaction.class));
    }

    @Test
    void findAllByWallet_ShouldReturnTransactions() throws DomainNotFoundException {
        // GIVEN
        Long walletId = 1L;
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(walletService.findById(walletId)).thenReturn(wallet);
        when(transactionRepository.findAllByWallet(wallet)).thenReturn(expectedTransactions);

        // WHEN
        List<Transaction> result = transactionService.findAllByWallet(walletId);

        // THEN
        assertEquals(expectedTransactions, result);
        verify(walletService).findById(walletId);
        verify(transactionRepository).findAllByWallet(wallet);
    }

    @Test
    void findAll_ShouldReturnAllTransactions() {
        // GIVEN
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        List<Transaction> expectedList = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<Transaction> result = transactionService.findAll();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository).findAll();
    }

    @Test
    void findById_ShouldReturnTransaction() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        // WHEN
        Transaction result = transactionService.findById(id);

        // THEN
        assertNotNull(result);
        assertEquals(transaction, result);
        verify(transactionRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            transactionService.findById(id);
        });
    }

    @Test
    void save_ShouldSaveTransaction() throws DomainNotFoundException, InsufficientBalanceException {
        // GIVEN
        Transaction transaction = new Transaction();
        when(transactionProcessingService.processTransaction(transaction)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // WHEN
        Transaction result = transactionService.save(transaction);

        // THEN
        assertNotNull(result);
        verify(transactionProcessingService).processTransaction(transaction);
        verify(transactionRepository).save(transaction);
        verify(entityUpdates).walletUpdate(transaction);
    }

    @Test
    void deleteById_ShouldInvokeRepositoryMethod() {
        // GIVEN
        Long id = 1L;

        // WHEN
        transactionService.deleteById(id);

        // THEN
        verify(transactionRepository).deleteById(id);
    }
}
