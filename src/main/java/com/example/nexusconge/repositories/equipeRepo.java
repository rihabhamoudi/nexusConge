package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface equipeRepo extends JpaRepository<equipe , Long> {
    List<equipe> findById(long idEquipe);
}
