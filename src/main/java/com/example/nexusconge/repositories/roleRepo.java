package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.ERole;
import com.example.nexusconge.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface roleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByNameRole(ERole nameRole);
}
