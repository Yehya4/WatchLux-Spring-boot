package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.paniers;
import com.example.miniecommerce.entities.utilisateurs;
import com.example.miniecommerce.repositories.paniersRepositorie;
import com.example.miniecommerce.repositories.utilisateursRepositorie;
import com.example.miniecommerce.repositories.lignePaniersRepositorie;
import com.example.miniecommerce.services.paniersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class paniersServiceImpl implements paniersService {

    @Autowired
    private paniersRepositorie paniersRepositorie;

    @Autowired
    private utilisateursRepositorie utilisateursRepositorie;

    @Autowired
    private lignePaniersRepositorie lignePaniersRepositorie;

    @Override
    public paniers getOrCreatePanierForUtilisateur(Long utilisateurId) {
        utilisateurs user = utilisateursRepositorie.findById(utilisateurId).orElse(null);
        if (user == null) {
            return null;
        }

        paniers panier = paniersRepositorie.findAll().stream()
                .filter(p -> p.getUtilisateurs() != null && p.getUtilisateurs().getId().equals(utilisateurId))
                .findFirst()
                .orElse(null);

        if (panier == null) {
            panier = paniers.builder()
                    .date(new Date())
                    .utilisateurs(user)
                    .lignePaniers(new ArrayList<>())
                    .build();
            panier = paniersRepositorie.save(panier);
        }
        return panier;
    }

    @Override
    public paniers save(paniers panier) {
        return paniersRepositorie.save(panier);
    }

    @Override
    public void clearPanier(Long panierId) {
        paniers panier = paniersRepositorie.findById(panierId).orElse(null);
        if (panier != null) {
            if (panier.getLignePaniers() != null && !panier.getLignePaniers().isEmpty()) {
                lignePaniersRepositorie.deleteAll(panier.getLignePaniers());
                panier.getLignePaniers().clear();
            }
            paniersRepositorie.save(panier);
        }
    }

    @Override
    public Double calculateTotal(Long panierId) {
        paniers panier = paniersRepositorie.findById(panierId).orElse(null);
        if (panier == null || panier.getLignePaniers() == null) {
            return 0.0;
        }
        return panier.getLignePaniers().stream()
                .mapToDouble(lp -> {
                    if (lp.getProduits() != null && lp.getProduits().getPrix() != null) {
                        return lp.getProduits().getPrix() * lp.getQuantite();
                    }
                    return 0.0;
                })
                .sum();
    }
}
