package com.example.nexusconge.services;

import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.userRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class userService {

@Autowired
userRepo userRepo;
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


}
