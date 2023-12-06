package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
    Match save(Match match);
    List<Match> findAll();
}
