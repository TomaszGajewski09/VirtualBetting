package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.ExchangeRate;
import com.projekt.VirtualBetting.model.types.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
//    List<ExchangeRate> findByBaseCodeAndUpdateDate(CurrencyType baseCode, LocalDate updateDate);
    Optional<ExchangeRate> findByBaseCodeAndUpdateDate(CurrencyType baseCode, LocalDate updateDate);
    List<ExchangeRate> findByBaseCode(CurrencyType baseCode);
    List<ExchangeRate> findAll();

}

