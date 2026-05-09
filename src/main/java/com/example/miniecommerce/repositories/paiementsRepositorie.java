package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.paiements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paiementsRepositorie extends JpaRepository<paiements, Long> {
}
