package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.utilisateurs;
import java.util.List;

public interface utilisateurService {
    utilisateurs findById(Long id);
    utilisateurs findByName(String nom);
    utilisateurs Save(utilisateurs utilisateurs);
    utilisateurs update(utilisateurs utilisateurs);
    utilisateurs delete(utilisateurs utilisateurs);
    utilisateurs findByEmail(String email);
    List<utilisateurs> findAll();
}
