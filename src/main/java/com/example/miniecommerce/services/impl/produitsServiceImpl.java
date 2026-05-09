package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.produits;
import com.example.miniecommerce.repositories.produitsRepositorie;
import com.example.miniecommerce.services.produitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class produitsServiceImpl implements produitsService {

    @Autowired
    private produitsRepositorie produitsRepositorie;

    @Override
    public produits findById(Long id) {

        return produitsRepositorie.findById(id).orElse(null);
    }

    @Override
    public List<produits> findAll() {
        return produitsRepositorie.findAll();
    }

    @Override
    public List<produits> findByCategorieId(Long categorieId) {
        return produitsRepositorie.findAll().stream()
                .filter(p -> p.getCategorie() != null && p.getCategorie().getId().equals(categorieId))
                .collect(Collectors.toList());
    }

    @Override
    public List<produits> searchByName(String keyword) {
        return produitsRepositorie.findAll().stream()
                .filter(p -> p.getNom() != null && p.getNom().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public produits save(produits produit) {
        return produitsRepositorie.save(produit);
    }

    @Override
    public produits update(produits produit) {
        return produitsRepositorie.save(produit);
    }

    @Override
    public void delete(Long id) {
        produitsRepositorie.deleteById(id);
    }

    @Override
    public produits updateStock(Long id, Integer quantite) {
        produits produit = findById(id);
        if (produit != null) {
            produit.setQuantite(quantite);
            return produitsRepositorie.save(produit);
        }
        return null;
    }
}
