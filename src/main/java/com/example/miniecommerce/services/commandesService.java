package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.commandes;
import com.example.miniecommerce.enums.commandeEnum;
import java.util.List;

public interface commandesService {
    commandes passerCommande(Long utilisateurId, String adresseLivraison);
    commandes findById(Long id);
    List<commandes> findByUtilisateurId(Long utilisateurId);
    List<commandes> findAll();
    commandes updateStatut(Long id, commandeEnum statut);
    void deleteCommande(Long id);
}
