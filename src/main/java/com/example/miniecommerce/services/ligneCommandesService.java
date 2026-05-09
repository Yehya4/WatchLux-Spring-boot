package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.ligneCommandes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ligneCommandesService {
    List<ligneCommandes> findByCommandeId(Long commandeId);
    ligneCommandes save(ligneCommandes ligneCommande);
}
