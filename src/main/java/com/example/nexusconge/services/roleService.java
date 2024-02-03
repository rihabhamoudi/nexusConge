package com.example.nexusconge.services;

import com.example.nexusconge.entities.Role;
import com.example.nexusconge.repositories.roleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class roleService {
    @Autowired
    roleRepo roleRepo;
    public int AjouterDelivery(Role E) {
        roleRepo.save(E);
        return E.getId();
    }
    public List<Role> getAllRole() {
        return roleRepo.findAll();
    }

    public void update(Role E) {
        roleRepo.save(E);
    }
    public void delete(int id) {

        roleRepo.deleteById(id);

    }

}
