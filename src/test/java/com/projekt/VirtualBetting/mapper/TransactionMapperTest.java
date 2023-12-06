package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import com.projekt.VirtualBetting.model.dto.TransactionDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import com.projekt.VirtualBetting.service.BettingSlipService;
import com.projekt.VirtualBetting.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {

    @Mock
    private BettingSlipService bettingSlipService;
    @Mock
    private WalletService walletService;

    @InjectMocks
    private TransactionMapper transactionMapper;

    @Test
    void mapToTransaction_ShouldMapCorrectly() throws DomainNotFoundException {
        // GIVEN
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(1l);
        transactionDTO.setBettingSlipId(1l);
        transactionDTO.setWalletId(1l);
        transactionDTO.setType("DEPOSIT");
        transactionDTO.setAmount(new BigDecimal(10.0));
        transactionDTO.setCurrency("PLN");
        transactionDTO.setBeforeTransaction(new BigDecimal(1.0));

        BettingSlip bettingSlip = new BettingSlip();

        when(bettingSlipService.findById(anyLong())).thenReturn(bettingSlip);


        Wallet wallet = new Wallet();
        when(walletService.findById(anyLong())).thenReturn(wallet);

        // WHEN
        Transaction result = transactionMapper.mapToTransaction(transactionDTO);

        // THEN
        assertNotNull(result);

    }

    @Test
    void mapToTransactionDTO_ShouldMapCorrectly() {
        // GIVEN
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1l);
        transaction.setBettingSlip(new BettingSlip());
        transaction.setWallet(new Wallet());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(new BigDecimal(10.0));
        transaction.setCurrency(CurrencyType.PLN);
        transaction.setBeforeTransaction(new BigDecimal(1.0));

        // WHEN
        TransactionDTO result = transactionMapper.mapToTransactionDTO(transaction);

        // THEN
        assertNotNull(result);

    }

    @Test
    void mapToTransactionDTOList_ShouldMapListCorrectly() {
        // GIVEN
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1l);
        transaction.setBettingSlip(new BettingSlip());
        transaction.setWallet(new Wallet());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(new BigDecimal(10.0));
        transaction.setCurrency(CurrencyType.PLN);
        transaction.setBeforeTransaction(new BigDecimal(1.0));


        List<Transaction> transactions = Arrays.asList(transaction);

        // WHEN
        List<TransactionDTO> result = transactionMapper.mapToTransactionDTOList(transactions);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());

    }
}

