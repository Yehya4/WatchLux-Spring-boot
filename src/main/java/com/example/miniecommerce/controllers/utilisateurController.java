package com.example.miniecommerce.controllers;

import com.example.miniecommerce.entities.categories;
import com.example.miniecommerce.entities.commandes;
import com.example.miniecommerce.entities.paniers;
import com.example.miniecommerce.entities.produits;
import com.example.miniecommerce.entities.utilisateurs;
import com.example.miniecommerce.enums.UtilisateurRoleEnum;
import com.example.miniecommerce.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class utilisateurController {

    @Autowired private produitsService produitsService;
    @Autowired private categoriesService categoriesService;
    @Autowired private paniersService paniersService;
    @Autowired private lignePaniersService lignePaniersService;
    @Autowired private commandesService commandesService;
    @Autowired private utilisateurService utilisateurService;

    private utilisateurs getOrInitClient(HttpSession session) {
        utilisateurs user = (utilisateurs) session.getAttribute("currentUser");
        if (user == null) {
            List<utilisateurs> tous = utilisateurService.findAll();
            for (utilisateurs u : tous) {
                if (u.getUtilisateurRole() == UtilisateurRoleEnum.Client) {
                    user = u; break;
                }
            }
            if (user == null) {
                user = utilisateurs.builder()
                        .nom("Alaoui").prenom("Youssef")
                        .email("client@watchlux.ma")
                        .UtilisateurRole(UtilisateurRoleEnum.Client)
                        .build();
                user = utilisateurService.Save(user);
            }
            session.setAttribute("currentUser", user);
            paniersService.getOrCreatePanierForUtilisateur(user.getId());
        }
        return user;
    }

    @GetMapping("/")
    public String accueil(Model model, HttpSession session) {
        getOrInitClient(session);
        model.addAttribute("produits", produitsService.findAll());
        model.addAttribute("categories", categoriesService.findAll());
        return "accueil";
    }

    @GetMapping("/produits")
    public String produits(@RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) String search,
                           Model model, HttpSession session) {
        getOrInitClient(session);
        List<produits> liste;
        if (search != null && !search.trim().isEmpty()) {
            liste = produitsService.searchByName(search);
        } else if (categoryId != null) {
            liste = produitsService.findByCategorieId(categoryId);
        } else {
            liste = produitsService.findAll();
        }
        model.addAttribute("produits", liste);
        model.addAttribute("categories", categoriesService.findAll());
        model.addAttribute("searchKeyword", search);
        model.addAttribute("selectedCategoryId", categoryId);
        return "produits";
    }

    @GetMapping("/panier")
    public String voirPanier(Model model, HttpSession session) {
        utilisateurs user = getOrInitClient(session);
        paniers panier = paniersService.getOrCreatePanierForUtilisateur(user.getId());
        model.addAttribute("panier", panier);
        model.addAttribute("total", paniersService.calculateTotal(panier.getId()));
        return "panier";
    }

    @PostMapping("/panier/ajouter")
    public String ajouterAuPanier(@RequestParam Long produitId,
                                  @RequestParam(defaultValue = "1") Integer quantite,
                                  HttpSession session) {
        utilisateurs user = getOrInitClient(session);
        paniers panier = paniersService.getOrCreatePanierForUtilisateur(user.getId());
        lignePaniersService.addProduitToPanier(panier.getId(), produitId, quantite);
        return "redirect:/panier";
    }

    @GetMapping("/panier/supprimer/{id}")
    public String supprimerDuPanier(@PathVariable Long id) {
        lignePaniersService.removeLignePanier(id);
        return "redirect:/panier";
    }

    @GetMapping("/panier/vider")
    public String viderPanier(HttpSession session) {
        utilisateurs user = getOrInitClient(session);
        paniers panier = paniersService.getOrCreatePanierForUtilisateur(user.getId());
        paniersService.clearPanier(panier.getId());
        return "redirect:/panier";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        utilisateurs user = getOrInitClient(session);
        paniers panier = paniersService.getOrCreatePanierForUtilisateur(user.getId());
        if (panier.getLignePaniers() == null || panier.getLignePaniers().isEmpty()) {
            return "redirect:/panier";
        }
        model.addAttribute("panier", panier);
        model.addAttribute("total", paniersService.calculateTotal(panier.getId()));
        return "checkout";
    }

    @PostMapping("/commander")
    public String commander(@RequestParam String adresse, HttpSession session) {
        utilisateurs user = getOrInitClient(session);
        try {
            commandes cmd = commandesService.passerCommande(user.getId(), adresse);
            return "redirect:/confirmation/" + cmd.getId();
        } catch (Exception e) {
            return "redirect:/panier?erreur=" + e.getMessage();
        }
    }

    @GetMapping("/confirmation/{id}")
    public String confirmation(@PathVariable Long id, Model model) {
        model.addAttribute("commande", commandesService.findById(id));
        return "confirmation";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("totalProduits", produitsService.findAll().size());
        model.addAttribute("totalCommandes", commandesService.findAll().size());
        return "admin/dashboard";
    }

    @GetMapping("/admin/produits")
    public String adminProduits(Model model) {
        model.addAttribute("categories", categoriesService.findAll());
        model.addAttribute("produits", produitsService.findAll());
        return "admin/ajouter-produit";
    }

    @PostMapping("/admin/produits")
    public String sauvegarderProduit(@RequestParam String nom,
                                     @RequestParam String description,
                                     @RequestParam Double prix,
                                     @RequestParam Integer quantite,
                                     @RequestParam Long categorieId) {
        categories cat = categoriesService.findById(categorieId);
        produits p = produits.builder()
                .nom(nom).description(description)
                .prix(prix).quantite(quantite).categorie(cat)
                .build();
        produitsService.save(p);
        return "redirect:/admin/produits?succes=true";
    }

    @GetMapping("/admin/produits/supprimer/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        produitsService.delete(id);
        return "redirect:/admin/produits";
    }

    @GetMapping("/admin/commandes")
    public String adminCommandes(Model model) {
        model.addAttribute("commandes", commandesService.findAll());
        return "admin/commandes";
    }
}
