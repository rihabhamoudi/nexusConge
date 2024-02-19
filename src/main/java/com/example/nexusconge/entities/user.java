package com.example.nexusconge.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;
    @Column(unique = true)
    private  Long matricule ;
    private  String username;
    private  String password ;
    private  String email;
    private  String solde_conge;
    //private String newPassword;
    @Enumerated(EnumType.STRING)
    private  activite activite;
    @ManyToMany
    @JsonIgnore
    private List<Conge> congeList;
    @ManyToOne
    equipe equipe;
    @ManyToOne
    NPlus1 nPlus1;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
