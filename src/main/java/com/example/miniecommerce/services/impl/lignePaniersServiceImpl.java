package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.lignePaniers;
import com.example.miniecommerce.entities.paniers;
import com.example.miniecommerce.entities.produits;
import com.example.miniecommerce.repositories.lignePaniersRepositorie;
import com.example.miniecommerce.repositories.paniersRepositorie;
import com.example.miniecommerce.repositories.produitsRepositorie;
import com.example.miniecommerce.services.lignePaniersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class lignePaniersServiceImpl implements lignePaniersService {

    @Autowired
    private lignePaniersRepositorie lignePaniersRepositorie;

    @Autowired
    private paniersRepositorie paniersRepositorie;

    @Autowired
    private produitsRepositorie produitsRepositorie;

    @Override
    public lignePaniers addProduitToPanier(Long panierId, Long produitId, Integer quantite) {
        paniers panier = paniersRepositorie.findById(panierId).orElse(null);
        produits produit = produitsRepositorie.findById(produitId).orElse(null);

        if (panier == null || produit == null) {
            return null;
        }

        lignePaniers ligneExistante = lignePaniersRepositorie.findAll().stream()
                .filter(lp -> lp.getPaniers() != null && lp.getPaniers().getId().equals(panierId)
                        && lp.getProduits() != null && lp.getProduits().getId().equals(produitId))
                .findFirst()
                .orElse(null);

        if (ligneExistante != null) {
            ligneExistante.setQuantite(ligneExistante.getQuantite() + quantite);
            return lignePaniersRepositorie.save(ligneExistante);
        } else {
            lignePaniers nouvelleLigne = lignePaniers.builder()
                    .paniers(panier)
                    .produits(produit)
                    .quantite(quantite)
                    .build();
            return lignePaniersRepositorie.save(nouvelleLigne);
        }
    }

    @Override
    public lignePaniers updateQuantite(Long lignePanierId, Integer quantite) {
        lignePaniers ligne = lignePaniersRepositorie.findById(lignePanierId).orElse(null);
        if (ligne != null) {
            ligne.setQuantite(quantite);
            return lignePaniersRepositorie.save(ligne);
        }
        return null;
    }

    @Override
    public void removeLignePanier(Long lignePanierId) {
        lignePaniersRepositorie.deleteById(lignePanierId);
    }

    @Override
    public List<lignePaniers> getLignesByPanierId(Long panierId) {
        return lignePaniersRepositorie.findAll().stream()
                .filter(lp -> lp.getPaniers() != null && lp.getPaniers().getId().equals(panierId))
                .collect(Collectors.toList());
    }
}
