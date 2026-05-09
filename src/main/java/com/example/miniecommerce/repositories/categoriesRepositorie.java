package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoriesRepositorie extends JpaRepository<categories, Long> {
    categories findByNom(String nom);
}
