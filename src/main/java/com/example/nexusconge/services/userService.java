package com.example.nexusconge.services;

import com.example.nexusconge.entities.*;
import com.example.nexusconge.repositories.equipeRepo;
import com.example.nexusconge.repositories.userRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class userService {

@Autowired
userRepo userRepo;
@Autowired
    equipeRepo equipeRepo;
    public Long AjouterUser(user user) {
        userRepo.save(user);
        return user.getIdUser();
    }

    public boolean signup(user u){
        user user1 = userRepo.findByUsername(u.getUsername()).orElse(null);
        user user2 = userRepo.findUserByEmail(u.getUsername()).orElse(null);

        if (user1 != null || user2 != null){
            return false;
        }else{
            userRepo.save(u);
            return true;
        }
    }
    public List<user> filtrerParActivite(activite activite) {
        return userRepo.findByActivite(activite);
    }

    public user assignUserToTeam(Long idEquipe, Long idUser) {
        user user = userRepo.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));

        equipe team = equipeRepo.findById(idEquipe)
                .orElseThrow(() -> new IllegalArgumentException("Invalid equipe id"));

        user.setEquipe(team);

        return userRepo.save(user);
    }



}
