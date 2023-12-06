package com.projekt.VirtualBetting.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.repository.BettingOptionRepository;
import com.projekt.VirtualBetting.repository.BettingSlipRepository;
import com.projekt.VirtualBetting.service.BettingSlipService;
import com.projekt.VirtualBetting.service.TransactionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BettingSlipServiceTest {

    @Mock
    private BettingSlipRepository bettingSlipRepository;

    @Mock
    private BettingOptionRepository bettingOptionRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BettingSlipService bettingSlipService;

    @Test
    void findAll_ReturnsListOfBettingSlips() {
        // GIVEN
        List<BettingSlip> expectedList = new ArrayList<>();
        when(bettingSlipRepository.findAll()).thenReturn(expectedList);

        // WHEN
        List<BettingSlip> result = bettingSlipService.findAll();

        // THEN
        assertEquals(expectedList, result);
        verify(bettingSlipRepository).findAll();
    }

    @Test
    void findById_ReturnsBettingSlip() throws DomainNotFoundException {
        // GIVEN
        Long id = 1L;
        BettingSlip expectedBettingSlip = new BettingSlip();
        when(bettingSlipRepository.findById(id)).thenReturn(Optional.of(expectedBettingSlip));

        // WHEN
        BettingSlip result = bettingSlipService.findById(id);

        // THEN
        assertEquals(expectedBettingSlip, result);
        verify(bettingSlipRepository).findById(id);
    }

    @Test
    void findById_ThrowsDomainNotFoundException() {
        // GIVEN
        Long id = 1L;
        when(bettingSlipRepository.findById(id)).thenReturn(Optional.empty());

        // THEN
        assertThrows(DomainNotFoundException.class, () -> {
            // WHEN
            bettingSlipService.findById(id);
        });
    }

    @Test
    void save_SavesAndReturnsBettingSlip() throws DomainNotFoundException, InsufficientBalanceException {
        // GIVEN
        BettingSlip bettingSlip = new BettingSlip();
        BettingSlip expectedSavedBettingSlip = new BettingSlip();
        when(bettingSlipRepository.save(bettingSlip)).thenReturn(expectedSavedBettingSlip);

        // WHEN
        BettingSlip result = bettingSlipService.save(bettingSlip);

        // THEN
        assertEquals(expectedSavedBettingSlip, result);
        verify(bettingSlipRepository).save(bettingSlip);
        verify(transactionService).initializeTransactionsForBettingSlip(bettingSlip);
    }

    @Test
    void deleteById_DeletesBettingSlip() {
        // GIVEN
        Long id = 1L;

        // WHEN
        bettingSlipService.deleteById(id);

        // THEN
        verify(bettingSlipRepository).deleteById(id);
    }

    @Test
    void deleteAll_DeletesAllBettingSlips() {
        // WHEN
        bettingSlipService.deleteAll();

        // THEN
        verify(bettingSlipRepository).deleteAll();
    }
}
