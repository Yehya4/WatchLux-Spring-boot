package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface utilisateursRepositorie extends JpaRepository<utilisateurs, Long> {
}
