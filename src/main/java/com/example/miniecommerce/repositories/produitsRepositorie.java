package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface produitsRepositorie extends JpaRepository<produits, Long> {
}
