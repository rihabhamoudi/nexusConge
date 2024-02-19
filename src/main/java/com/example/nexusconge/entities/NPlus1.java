package com.example.nexusconge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NPlus1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nplus", nullable = false)
    private Long idNplus;

    private  String name;
    private  String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nPlus1" , fetch = FetchType.LAZY)
    @JsonIgnore
    List<user> usersLIST;
}
