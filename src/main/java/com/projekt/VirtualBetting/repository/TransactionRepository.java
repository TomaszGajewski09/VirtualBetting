package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.Transaction;
import com.projekt.VirtualBetting.model.domains.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAll();
    Optional<Transaction> findById(Long id);
    List<Transaction> findAllByWallet(Wallet wallet);
    Transaction save(Transaction transaction);
    void deleteById(Long id);
}
