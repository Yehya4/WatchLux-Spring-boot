package com.example.miniecommerce.entities;

import com.example.miniecommerce.enums.commandeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class commandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCommande;
    private commandeEnum commandestatut;
    private Double montant;
    private String adresseDeLivraison;

    @ManyToOne
    @JoinColumn
    private utilisateurs utilisateur;

    @OneToMany(mappedBy = "commandes" , fetch = FetchType.LAZY)
    private List<ligneCommandes> ligneCommandes = new ArrayList<>();

    @OneToOne(mappedBy = "commandes" , fetch = FetchType.LAZY)
    private paiements paiements;
}
