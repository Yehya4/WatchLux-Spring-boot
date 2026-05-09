package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.produits;
import java.util.List;

public interface produitsService {
    produits findById(Long id);
    List<produits> findAll();
    List<produits> findByCategorieId(Long categorieId);
    List<produits> searchByName(String keyword);
    produits save(produits produit);
    produits update(produits produit);
    void delete(Long id);
    produits updateStock(Long id, Integer quantite);
}
