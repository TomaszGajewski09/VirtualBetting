package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.mapper.WalletMapper;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class EntityUpdatesTest {

    @Mock
    private WalletService walletService;

    @Mock
    private WalletMapper walletMapper;

    @InjectMocks
    private EntityUpdates entityUpdates;

    @Test
    void walletUpdate_ShouldUpdateBalance() throws DomainNotFoundException {
        // GIVEN
        Long walletId = 1L;
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(BigDecimal.valueOf(100));
        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(50));

        Wallet updatedWallet = new Wallet();
        updatedWallet.setWalletId(walletId);
        updatedWallet.setBalance(BigDecimal.valueOf(150));

        WalletDTO walletDto = new WalletDTO();
        walletDto.setBalance(BigDecimal.valueOf(150));

        when(walletService.findById(walletId)).thenReturn(wallet);
        when(walletMapper.mapToWalletDto(any(Wallet.class))).thenReturn(walletDto);

        // WHEN
        entityUpdates.walletUpdate(transaction);

        // THEN
        verify(walletService).updateWallet(eq(walletId), any(WalletDTO.class));
        assertEquals(BigDecimal.valueOf(150), wallet.getBalance());
    }

    @Test
    void walletUpdate_ThrowsException_IfWalletNotFound() throws DomainNotFoundException {
        // GIVEN
        Long walletId = 2L;
        Transaction transaction = new Transaction();
        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        transaction.setWallet(wallet);
        transaction.setAmount(BigDecimal.valueOf(50));

        when(walletService.findById(walletId)).thenThrow(new DomainNotFoundException("Wallet not found"));

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            entityUpdates.walletUpdate(transaction);
        });
    }
}

