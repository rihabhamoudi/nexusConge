package com.example.nexusconge.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conge", nullable = false)
    private Long idConge;
    private Date datedebut;
    private  Date datefin;
    @Enumerated(EnumType.STRING)
    private typeConge type ;
    private  String raison ;
    private byte[] data;
    @ManyToMany(mappedBy = "congeList" , cascade = CascadeType.ALL)
    private List<user> users;

    @JsonIgnore
    public List<user> getUsers() {
        return users;}

}
