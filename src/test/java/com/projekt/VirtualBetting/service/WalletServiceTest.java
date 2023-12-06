package com.projekt.VirtualBetting.service;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.WalletDTO;
import com.projekt.VirtualBetting.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void findAll_ShouldReturnAllWallets() {
        // GIVEN
        Wallet wallet1 = new Wallet();
        Wallet wallet2 = new Wallet();
        List<Wallet> expectedWallets = Arrays.asList(wallet1, wallet2);
        when(walletRepository.findAll()).thenReturn(expectedWallets);

        // WHEN
        List<Wallet> result = walletService.findAll();

        // THEN
        assertEquals(expectedWallets, result);
        verify(walletRepository).findAll();
    }

    @Test
    void findById_ShouldReturnWallet() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        Wallet expectedWallet = new Wallet();
        when(walletRepository.findById(id)).thenReturn(Optional.of(expectedWallet));

        // WHEN
        Wallet result = walletService.findById(id);

        // THEN
        assertEquals(expectedWallet, result);
        verify(walletRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(walletRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            walletService.findById(id);
        });
    }

    @Test
    void save_ShouldReturnSavedWallet() {
        // GIVEN
        Wallet wallet = new Wallet();
        when(walletRepository.save(wallet)).thenReturn(wallet);

        // WHEN
        Wallet result = walletService.save(wallet);

        // THEN
        assertEquals(wallet, result);
        verify(walletRepository).save(wallet);
    }

    @Test
    void updateWallet_ShouldReturnUpdatedWallet() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        Wallet existingWallet = new Wallet();
        WalletDTO walletDTO = new WalletDTO();
        when(walletRepository.findById(id)).thenReturn(Optional.of(existingWallet));
        when(walletRepository.save(any(Wallet.class))).thenReturn(existingWallet);

        // WHEN
        Wallet result = walletService.updateWallet(id, walletDTO);

        // THEN
        assertNotNull(result);
        verify(walletRepository).findById(id);
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void deleteById_ShouldInvokeDelete() {
        // GIVEN
        Long id = 1L;

        // WHEN
        walletService.deleteById(id);

        // THEN
        verify(walletRepository).deleteById(id);
    }
}
