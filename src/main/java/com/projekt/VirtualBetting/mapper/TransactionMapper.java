package com.projekt.VirtualBetting.mapper;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.model.domains.BettingSlip;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.dto.TransactionDTO;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import com.projekt.VirtualBetting.model.types.TransactionType;
import com.projekt.VirtualBetting.service.BettingSlipService;
import com.projekt.VirtualBetting.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionMapper {

    private final BettingSlipService bettingSlipService;
    private final WalletService walletService;



    public Transaction mapToTransaction(TransactionDTO transactionDTO) throws DomainNotFoundException {
        Long bettingSlipId = transactionDTO.getBettingSlipId();
        Long walletId = transactionDTO.getWalletId();

        return Transaction.builder()
                .transactionId(transactionDTO.getTransactionId())
                .bettingSlip((bettingSlipId != null)? bettingSlipService.findById(bettingSlipId) : null)
                .wallet((walletId != null)? walletService.findById(walletId) : null)
                .type(TransactionType.getIfValid(transactionDTO.getType()))
                .amount(transactionDTO.getAmount())
                .currency(CurrencyType.getIfValid(transactionDTO.getCurrency()))
                .beforeTransaction(transactionDTO.getBeforeTransaction())
                .transactionDateTime(transactionDTO.getTransactionDateTime())
                .description(transactionDTO.getDescription())
                .build();
    }

    public TransactionDTO mapToTransactionDTO(Transaction transaction) {
        BettingSlip bettingSlip = transaction.getBettingSlip();
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .bettingSlipId((bettingSlip != null)? bettingSlip.getBettingSlipId() : null)
                .walletId(transaction.getWallet().getWalletId())
                .type(transaction.getType().toString())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency().toString())
                .beforeTransaction(transaction.getBeforeTransaction())
                .transactionDateTime(transaction.getTransactionDateTime())
                .description(transaction.getDescription())
                .build();
    }

    public List<TransactionDTO> mapToTransactionDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::mapToTransactionDTO)
                .collect(Collectors.toList());
    }
}
