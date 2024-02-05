package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.typeConge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Repository
public interface congeRepo  extends JpaRepository<Conge, Long> {
    default Conge store(MultipartFile file) throws IOException {
        Conge conge = new Conge();
        conge.setData(file.getBytes());
        return save(conge);
    }

    List<Conge> findByDatedebut(Date datedebut);
    List<Conge> findByRaison(String raison);
    List <Conge> findByType(typeConge type);
   /* @Query("SELECT c FROM Conge c WHERE c.datedebut BETWEEN :startDate AND :endDate")
    List<Conge> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);*/

}
