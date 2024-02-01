package com.example.nexusconge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipe", nullable = false)
    private Long idEquipe;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipe" , fetch = FetchType.LAZY)
    @JsonIgnore
    List<user> users;


}
