package com.example.nexusconge.controllers;


import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.repositories.congeRepo;
import com.example.nexusconge.services.congeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/conge")
public class congeController {
    @Autowired
    congeService  congeService;
    @Autowired
    congeRepo  congeRepo;

    @PostMapping("/addconge")
    public void ajouterSprint(@RequestBody Conge conge){
        congeService.AjouterConge(conge);
    }

    @GetMapping(value = "/conges")
    @ResponseBody
    public List<Conge> getAllconges() {
        return congeService.getAllConge();
    }


    @PutMapping("/updateconge")
    @ResponseBody
    public void updateConge(@RequestBody Conge conge){
        congeService.update(conge);
    }

    @DeleteMapping("/deleteconge/{idConge}")
    public ResponseEntity<String> deleteConge(@PathVariable Long idConge) {
        congeService.delete(idConge);
        return ResponseEntity.ok("Congé supprimé avec succès");
    }


    @PostMapping("/upload")
    public ResponseEntity<Conge> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Conge conge = congeRepo.store(file);
            return ResponseEntity.status(HttpStatus.OK).body(conge);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}