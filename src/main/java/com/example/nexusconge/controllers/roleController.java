package com.example.nexusconge.controllers;

import com.example.nexusconge.entities.Role;
import com.example.nexusconge.services.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Role")
public class roleController {
    @Autowired
  roleService roleService;


    @PostMapping("/addsprint")
    public void ajouterRole(@RequestBody Role role){
        roleService.AjouterDelivery(role);
    }
    @GetMapping(value = "/sprints")
    @ResponseBody
    public List<Role> getAllroles() {
        return roleService.getAllRole();
    }


    @PutMapping("/updateSprint")
    @ResponseBody
    public void updaterole(@RequestBody Role R){
        roleService.update(R);
    }

    @DeleteMapping("/deleteSprint/{id}")
    @ResponseBody
    public void deleterole (@PathVariable  int  id ){
        roleService.delete(id);
    }

}


