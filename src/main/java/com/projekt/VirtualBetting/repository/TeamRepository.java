package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.League;
import com.projekt.VirtualBetting.model.domains.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByLeague(League league);
}

