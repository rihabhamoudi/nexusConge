package com.example.nexusconge.controllers;


import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.typeConge;
import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.congeRepo;
import com.example.nexusconge.services.congeService;
import com.example.nexusconge.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/conge")
public class congeController {
    @Autowired
    congeService  congeService;
    @Autowired
    congeRepo  congeRepo;
    @Autowired
    userService userService;

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
    @GetMapping("/findByStartDate")
    public ResponseEntity<List<Conge>> findByStartDate(@RequestParam Date datedebut) {
        try {
            List<Conge> result = congeService.findByStartDate(datedebut);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByReason")
    public ResponseEntity<List<Conge>> findByReason(@RequestParam String raison) {
        try {
            List<Conge> result = congeService.findByReason(raison);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByType")
    public ResponseEntity<List<Conge>> findByType(@RequestParam typeConge type) {
        try {
            List<Conge> result = congeService.findByType(type);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }}
    @PostMapping("/createConge")
    public ResponseEntity<Conge> createConge(@RequestBody CreateCongeRequest request) {
        Conge conge = congeService.createConge(request.getUserId(), request.getDateDebut(), request.getDateFin(), request.getRaison());
        return new ResponseEntity<>(conge, HttpStatus.CREATED);
    }

    static class CreateCongeRequest {
        private Long userId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dateDebut;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dateFin;
        private String raison;

        // Getters and setters
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Date getDateDebut() {
            return dateDebut;
        }

        public void setDateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
        }

        public Date getDateFin() {
            return dateFin;
        }

        public void setDateFin(Date dateFin) {
            this.dateFin = dateFin;
        }

        public String getRaison() {
            return raison;
        }

        public void setRaison(String raison) {
            this.raison = raison;
        }
    }
   }

