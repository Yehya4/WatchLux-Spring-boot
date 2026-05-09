package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.paniers;

public interface paniersService {
    paniers getOrCreatePanierForUtilisateur(Long utilisateurId);
    paniers save(paniers panier);
    void clearPanier(Long panierId);
    Double calculateTotal(Long panierId);
}
