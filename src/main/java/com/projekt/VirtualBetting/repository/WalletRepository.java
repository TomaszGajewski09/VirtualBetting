package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    Wallet save(Wallet wallet);
    List<Wallet> findAll();
    Optional<Wallet> findById(Long walletId);
    void deleteById(Long id);
    void deleteAll();
}
