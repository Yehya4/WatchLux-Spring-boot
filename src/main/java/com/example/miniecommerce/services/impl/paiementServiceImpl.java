package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.commandes;
import com.example.miniecommerce.entities.paiements;
import com.example.miniecommerce.enums.commandeEnum;
import com.example.miniecommerce.enums.paiementEnum;
import com.example.miniecommerce.enums.statutPaiementEnum;
import com.example.miniecommerce.repositories.commandesRepositorie;
import com.example.miniecommerce.repositories.paiementsRepositorie;
import com.example.miniecommerce.services.paiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class paiementServiceImpl implements paiementService {

    @Autowired
    private paiementsRepositorie paiementsRepositorie;

    @Autowired
    private commandesRepositorie commandesRepositorie;

    @Override
    public paiements effectuerPaiement(Long commandeId, paiementEnum methode, Double montant) {
        commandes commande = commandesRepositorie.findById(commandeId).orElse(null);
        if (commande == null) {
            return null;
        }

        paiements paiement = paiements.builder()
                .MethodePaiement(methode)
                .statutPaiement(statutPaiementEnum.APPROUVE)
                .montant(montant)
                .datePaiement(new Date())
                .commandes(commande)
                .build();

        paiements savedPaiement = paiementsRepositorie.save(paiement);

        commande.setCommandestatut(commandeEnum.CONFIRMEE);
        commandesRepositorie.save(commande);

        return savedPaiement;
    }

    @Override
    public paiements findByCommandeId(Long commandeId) {
        return paiementsRepositorie.findAll().stream()
                .filter(p -> p.getCommandes() != null && p.getCommandes().getId().equals(commandeId))
                .findFirst()
                .orElse(null);
    }
}
