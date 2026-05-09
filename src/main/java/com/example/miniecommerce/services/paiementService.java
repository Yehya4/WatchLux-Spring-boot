package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.paiements;
import com.example.miniecommerce.enums.paiementEnum;

public interface paiementService {
    paiements effectuerPaiement(Long commandeId, paiementEnum methode, Double montant);
    paiements findByCommandeId(Long commandeId);
}
