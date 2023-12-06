package com.projekt.VirtualBetting.controller;

import com.projekt.VirtualBetting.exception.DomainNotFoundException;
import com.projekt.VirtualBetting.exception.InsufficientBalanceException;
import com.projekt.VirtualBetting.mapper.TransactionMapper;
import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.dto.TransactionDTO;
import com.projekt.VirtualBetting.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionMapper.mapToTransactionDTOList(transactionService.findAll()));
    }

    // todo: ByWallet czy ByUser?
    @GetMapping("/wallets/{id}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionByWallet(@PathVariable Long id) throws DomainNotFoundException {
        List<Transaction> transactions = transactionService.findAllByWallet(id);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDTOList(transactions));
    }

    @PostMapping()
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO details) throws DomainNotFoundException, InsufficientBalanceException {
        Transaction transaction = transactionMapper.mapToTransaction(details);
        Transaction createdTransaction = transactionService.save(transaction);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDTO(createdTransaction));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) throws DomainNotFoundException {
//        Transaction transaction = transactionService.findById(id);
//        Transaction updatedTransaction = transactionService.save(transaction);
//        return ResponseEntity.ok(updatedTransaction);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

