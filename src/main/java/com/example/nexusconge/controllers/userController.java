package com.example.nexusconge.controllers;


import com.example.nexusconge.entities.*;
import com.example.nexusconge.repositories.roleRepo;
import com.example.nexusconge.repositories.userRepo;
import com.example.nexusconge.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class userController {
    @Autowired
    userService userService;
    @Autowired
    private userRepo userRepo;

    @Autowired
    private roleRepo roleRepo;

    @PostMapping("/adduser")
   public void ajouterUser(@RequestBody user user){
        userService.AjouterUser(user);
    }

    @GetMapping
    @ResponseBody
    public List<user> getAllUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/{idUser}")
    @ResponseBody
    public user changeRole(@RequestBody List<String> myRoles , @PathVariable long idUser) {
        user user = userRepo.findById(idUser).orElse(null);
        Set<Role> roles = new HashSet<>();
        myRoles.forEach(role -> {
            switch (role){
                case "RH":
                    Role rhrole= roleRepo.findByNameRole(ERole.RH).orElse(null);
                    roles.add(rhrole);
                    break;
                case "EMPLOYE":
                    Role EMPLOYErole= roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null);
                    roles.add(EMPLOYErole);
                    break;
                case "NPLUS1":
                    Role nplus1role= roleRepo.findByNameRole(ERole.NPlus1).orElse(null);
                    roles.add(nplus1role);
                    break;
                default:
                    Role defaultrole= roleRepo.findByNameRole(ERole.EMPLOYE).orElse(null);
                    roles.add(defaultrole);

            }
        });
        user u = new user();
        u.setIdUser(user.getIdUser());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setUsername(user.getUsername());
        u.setRoles(roles);

        return userRepo.save(u);
    }

    @GetMapping("/findByAct")
    public ResponseEntity<List<user>> filtrerParActivite(@RequestParam activite activite) {
        try {
            List<user> result = userService.filtrerParActivite(activite);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/assign/{idEquipe}/{idUser}")
    public user assignTeamToUser(@PathVariable Long idEquipe, @PathVariable Long idUser) {
        return userService.assignUserToTeam(idEquipe, idUser);
    }

    @PostMapping("/assignrole/{id}/{idUser}")
    public user assignUserToRole(@PathVariable Integer id, @PathVariable Long idUser) {
        return userService.assignUserToRole(id, idUser);
    }
    }
