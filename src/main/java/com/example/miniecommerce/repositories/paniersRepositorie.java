package com.example.miniecommerce.repositories;

import com.example.miniecommerce.entities.paniers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paniersRepositorie extends JpaRepository<paniers, Long> {
}
