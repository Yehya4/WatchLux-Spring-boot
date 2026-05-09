package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.commandes;
import com.example.miniecommerce.entities.ligneCommandes;
import com.example.miniecommerce.entities.lignePaniers;
import com.example.miniecommerce.entities.paniers;
import com.example.miniecommerce.entities.produits;
import com.example.miniecommerce.entities.utilisateurs;
import com.example.miniecommerce.enums.commandeEnum;
import com.example.miniecommerce.repositories.commandesRepositorie;
import com.example.miniecommerce.repositories.ligneCommandesRepositorie;
import com.example.miniecommerce.repositories.lignePaniersRepositorie;
import com.example.miniecommerce.repositories.paniersRepositorie;
import com.example.miniecommerce.repositories.produitsRepositorie;
import com.example.miniecommerce.repositories.utilisateursRepositorie;
import com.example.miniecommerce.repositories.paiementsRepositorie;
import com.example.miniecommerce.services.commandesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class commandesServiceImpl implements commandesService {

    private commandesRepositorie commandesRepositorie;
    private utilisateursRepositorie utilisateursRepositorie;
    private paniersRepositorie paniersRepositorie;
    private ligneCommandesRepositorie ligneCommandesRepositorie;
    private lignePaniersRepositorie lignePaniersRepositorie;
    private produitsRepositorie produitsRepositorie;
    private paiementsRepositorie paiementsRepositorie;

    @Override
    public commandes passerCommande(Long utilisateurId, String adresseLivraison) {
        utilisateurs user = utilisateursRepositorie.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + utilisateurId));
        paniers panier = paniersRepositorie.findAll().stream()
                .filter(p -> p.getUtilisateurs() != null && p.getUtilisateurs().getId().equals(utilisateurId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Panier non trouvé pour l'utilisateur avec l'id : " + utilisateurId));

        List<lignePaniers> cartLines = panier.getLignePaniers();
        if (cartLines == null || cartLines.isEmpty()) {
            throw new RuntimeException("Le panier est vide. Impossible de passer une commande.");
        }

        // 3. Calculer le montant total
        double total = 0.0;
        for (lignePaniers lp : cartLines) {
            if (lp.getProduits() != null && lp.getProduits().getPrix() != null) {
                total += lp.getProduits().getPrix() * lp.getQuantite();
            }
        }

        // 4. Créer et enregistrer la commande
        commandes commande = commandes.builder()
                .dateCommande(new Date())
                .commandestatut(commandeEnum.EN_ATTENTE)
                .montant(total)
                .adresseDeLivraison(adresseLivraison)
                .utilisateur(user)
                .ligneCommandes(new ArrayList<>())
                .build();

        commandes savedCommande = commandesRepositorie.save(commande);

        // 5. Transférer les lignes du panier vers les lignes de commande et réduire le stock des produits
        List<ligneCommandes> orderLines = new ArrayList<>();
        for (lignePaniers lp : cartLines) {
            produits prod = lp.getProduits();
            if (prod != null) {
                // Vérification et mise à jour du stock
                int currentQty = prod.getQuantite() != null ? prod.getQuantite() : 0;
                int orderedQty = lp.getQuantite() != null ? lp.getQuantite() : 0;
                if (currentQty < orderedQty) {
                    throw new RuntimeException("Stock insuffisant pour le produit : " + prod.getNom());
                }
                prod.setQuantite(currentQty - orderedQty);
                produitsRepositorie.save(prod);

                // Créer la ligne de commande
                ligneCommandes lc = ligneCommandes.builder()
                        .quantite(orderedQty)
                        .prixUnitaire(prod.getPrix() != null ? prod.getPrix() : 0.0)
                        .produit(prod)
                        .commandes(savedCommande)
                        .build();

                ligneCommandes savedLc = ligneCommandesRepositorie.save(lc);
                orderLines.add(savedLc);
            }
        }

        savedCommande.setLigneCommandes(orderLines);

        // 6. Vider le panier (supprimer les lignes de panier de la base de données et de la liste en mémoire)
        lignePaniersRepositorie.deleteAll(cartLines);
        cartLines.clear();
        paniersRepositorie.save(panier);

        return savedCommande;
    }

    @Override
    public commandes findById(Long id) {
        return commandesRepositorie.findById(id).orElse(null);
    }

    @Override
    public List<commandes> findByUtilisateurId(Long utilisateurId) {
        return commandesRepositorie.findAll().stream()
                .filter(c -> c.getUtilisateur() != null && c.getUtilisateur().getId().equals(utilisateurId))
                .collect(Collectors.toList());
    }

    @Override
    public List<commandes> findAll() {
        return commandesRepositorie.findAll();
    }

    @Override
    public commandes updateStatut(Long id, commandeEnum statut) {
        commandes commande = commandesRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'id : " + id));
        commande.setCommandestatut(statut);
        return commandesRepositorie.save(commande);
    }

    @Override
    public void deleteCommande(Long id) {
        commandes commande = commandesRepositorie.findById(id).orElse(null);
        if (commande != null) {
            // Supprimer le paiement associé s'il existe
            if (commande.getPaiements() != null) {
                paiementsRepositorie.delete(commande.getPaiements());
            }
            // Supprimer les lignes de commande associées s'il y en a
            if (commande.getLigneCommandes() != null && !commande.getLigneCommandes().isEmpty()) {
                ligneCommandesRepositorie.deleteAll(commande.getLigneCommandes());
            }
            // Supprimer la commande
            commandesRepositorie.delete(commande);
        }
    }
}
