package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.commandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface commandesRepositorie extends JpaRepository<commandes, Long> {
}
