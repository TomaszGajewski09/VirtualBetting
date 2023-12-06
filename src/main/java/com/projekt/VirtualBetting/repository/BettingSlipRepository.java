package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.BettingSlip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BettingSlipRepository extends CrudRepository<BettingSlip, Long> {
    BettingSlip save(BettingSlip bettingSlip);

    List<BettingSlip> findAll();
}
