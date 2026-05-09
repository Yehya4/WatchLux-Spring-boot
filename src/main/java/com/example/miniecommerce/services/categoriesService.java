package com.example.miniecommerce.services;

import com.example.miniecommerce.entities.categories;
import java.util.List;

public interface categoriesService {
    categories findById(Long id);
    categories findByName(String name);
    categories Save(categories categories);
    categories update(categories categories);
    categories delete(categories categories);
    List<categories> findAll();
}
