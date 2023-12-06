package com.projekt.VirtualBetting.repository;

import com.projekt.VirtualBetting.model.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long userId);
    void deleteById(Long userId);
    void deleteAll();
}
