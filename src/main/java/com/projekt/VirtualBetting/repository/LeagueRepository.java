package com.projekt.VirtualBetting.repository;


import com.projekt.VirtualBetting.model.domains.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {
    List<League> findAll();
}
