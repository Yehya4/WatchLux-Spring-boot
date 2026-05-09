package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.lignePaniers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface lignePaniersRepositorie extends JpaRepository<lignePaniers, Long> {
}
