package com.example.nexusconge.services;

import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.typeConge;
import com.example.nexusconge.repositories.congeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class congeService {
    @Autowired
    congeRepo congeRepo;

    public Long AjouterConge(Conge E) {
        congeRepo.save(E);
        return E.getIdConge();
    }


    public List<Conge> getAllConge() {
        return congeRepo.findAll();
    }


    public void update(Conge E) {
        congeRepo.save(E);
    }

    public void delete(Long  idConge) {
        System.out.println("tryyyyyy"+idConge);
        congeRepo.deleteById(idConge);

    }

    public List<Conge> findByStartDate(Date datedebut) {
        return congeRepo.findByDatedebut(datedebut);
    }

    public List<Conge> findByReason(String raison) {
        return congeRepo.findByRaison(raison);
    }

    public List<Conge> findByType(typeConge type) {
        return congeRepo.findByType(type);
    }/*
    public List<Conge> findByDateRange(Date startDate, Date endDate) {
        return congeRepo.findByDateRange(startDate, endDate);
    }*/

}
