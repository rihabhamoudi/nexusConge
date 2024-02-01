package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository <user, Long> {
    Optional<user> findByUsername(String username);
    Optional<user> findUserByEmail(String email);
}
