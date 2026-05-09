package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.ligneCommandes;
import com.example.miniecommerce.repositories.ligneCommandesRepositorie;
import com.example.miniecommerce.services.ligneCommandesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ligneCommandesServiceImpl implements ligneCommandesService {

    @Autowired
    private ligneCommandesRepositorie ligneCommandesRepositorie;

    @Override
    public List<ligneCommandes> findByCommandeId(Long commandeId) {
        return ligneCommandesRepositorie.findAll().stream()
                .filter(lc -> lc.getCommandes() != null && lc.getCommandes().getId().equals(commandeId))
                .collect(Collectors.toList());
    }

    @Override
    public ligneCommandes save(ligneCommandes ligneCommande) {
        return ligneCommandesRepositorie.save(ligneCommande);
    }
}
