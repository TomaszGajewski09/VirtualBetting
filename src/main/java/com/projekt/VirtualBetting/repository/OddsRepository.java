package com.projekt.VirtualBetting.repository;


import com.projekt.VirtualBetting.model.domains.Match;
import com.projekt.VirtualBetting.model.domains.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OddsRepository extends CrudRepository<Odds, Long> {
    Optional<Odds> findByMatch(Match match);
    List<Odds> findAll();
}

