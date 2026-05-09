package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.lignePaniers;
import java.util.List;

public interface lignePaniersService {
    lignePaniers addProduitToPanier(Long panierId, Long produitId, Integer quantite);
    lignePaniers updateQuantite(Long lignePanierId, Integer quantite);
    void removeLignePanier(Long lignePanierId);
    List<lignePaniers> getLignesByPanierId(Long panierId);
}
