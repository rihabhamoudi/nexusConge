package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.activite;
import com.example.nexusconge.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository <user, Long> {
    Optional<user> findByUsername(String username);
    Optional<user> findUserByEmail(String email);
    List <user> findByActivite(activite activite);
    @Query("SELECT DISTINCT u FROM user u LEFT JOIN FETCH u.congeList")
    List<user> findAllWithCongeList();
}
