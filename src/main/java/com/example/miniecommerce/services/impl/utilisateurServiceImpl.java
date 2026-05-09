package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.utilisateurs;
import com.example.miniecommerce.repositories.utilisateursRepositorie;
import com.example.miniecommerce.services.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class utilisateurServiceImpl implements utilisateurService {

    @Autowired
    private utilisateursRepositorie utilisateursRepositorie;

    @Override
    public utilisateurs findById(Long id) {
        return utilisateursRepositorie.findById(id).orElse(null);
    }

    @Override
    public utilisateurs findByName(String nom) {
        return utilisateursRepositorie.findAll().stream()
                .filter(u -> u.getNom() != null && u.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    @Override
    public utilisateurs Save(utilisateurs utilisateurs) {
        return utilisateursRepositorie.save(utilisateurs);
    }

    @Override
    public utilisateurs update(utilisateurs utilisateurs) {
        return utilisateursRepositorie.save(utilisateurs);
    }

    @Override
    public utilisateurs delete(utilisateurs utilisateurs) {
        utilisateursRepositorie.delete(utilisateurs);
        return utilisateurs;
    }

    @Override
    public utilisateurs findByEmail(String email) {
        return utilisateursRepositorie.findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<utilisateurs> findAll() {
        return utilisateursRepositorie.findAll();
    }
}
