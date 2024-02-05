package com.example.nexusconge.controllers;


import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.typeConge;
import com.example.nexusconge.repositories.congeRepo;
import com.example.nexusconge.services.congeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        }
    }/*
    @GetMapping("/findByDateRange")
    public ResponseEntity<List<Conge>> findByDateRange(
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate) {
        try {
            List<Conge> result = congeService.findByDateRange(startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }*/
}
