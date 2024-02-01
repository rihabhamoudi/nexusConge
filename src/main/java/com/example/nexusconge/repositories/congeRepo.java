package com.example.nexusconge.repositories;

import com.example.nexusconge.entities.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Repository
public interface congeRepo  extends JpaRepository<Conge, Long> {
    default Conge store(MultipartFile file) throws IOException {
        Conge conge = new Conge();
        conge.setData(file.getBytes());
        return save(conge);
    }

}
