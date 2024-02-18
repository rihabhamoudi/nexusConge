package com.example.nexusconge.services;

import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.activite;
import com.example.nexusconge.entities.typeConge;
import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.congeRepo;
import com.example.nexusconge.repositories.userRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class congeService {
    @Autowired
    congeRepo congeRepo;
    @Autowired
    userRepo userRepo;

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
    }
    @Transactional
    public Conge createConge(Long userId, Date dateDebut, Date dateFin, String raison) {
        user user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Création du congé
        Conge conge = new Conge();
        conge.setDatedebut(dateDebut);
        conge.setDatefin(dateFin);
        conge.setRaison(raison);

        // Initialisation de la liste users si elle est null
        if (conge.getUsers() == null) {
            conge.setUsers(new ArrayList<>());
        }

        // Ajout de l'utilisateur au congé
        conge.getUsers().add(user);

        // Ajout du congé à la liste de congés de l'utilisateur
        user.getCongeList().add(conge);

        // Sauvegarde du congé
        return congeRepo.save(conge);
    }



    /*
    public List<Conge> findByDateRange(Date startDate, Date endDate) {
        return congeRepo.findByDateRange(startDate, endDate);
    }*/

}
