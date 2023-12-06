package com.projekt.VirtualBetting.repository;


import com.projekt.VirtualBetting.model.domains.BettingOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BettingOptionRepository extends CrudRepository<BettingOption, Long> {

}
