package com.example.miniecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class produits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer quantite;
    private String image;

    @ManyToOne
    private categories categorie;

    @OneToMany(mappedBy = "produits" , fetch = FetchType.LAZY)
    private List<lignePaniers> lignePaniers = new ArrayList<>();

    @OneToMany(mappedBy = "produit" , fetch = FetchType.LAZY)
    private List<ligneCommandes> ligneCommandes = new ArrayList<>();

}
