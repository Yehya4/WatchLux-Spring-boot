package com.example.miniecommerce.services.impl;

import com.example.miniecommerce.entities.categories;
import com.example.miniecommerce.repositories.categoriesRepositorie;
import com.example.miniecommerce.services.categoriesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class categoriesServiceImpl implements categoriesService {

    private categoriesRepositorie categoriesRepositorie;

    @Override
    public categories findById(Long id) {

        return categoriesRepositorie.findById(id).orElse(null);
    }

    @Override
    public categories findByName(String name) {
        categories result = categoriesRepositorie.findByNom(name);
        if (result == null) {
            throw new RuntimeException("Catégorie non trouvée avec le nom : " + name);
        }
        return result;
    }

    @Override
    public categories Save(categories categories) {

        return categoriesRepositorie.save(categories);
    }

    @Override
    public categories update(categories categories) {

        return categoriesRepositorie.save(categories);
    }

    @Override
    public categories delete(categories categories) {
        categoriesRepositorie.delete(categories);
        return categories;
    }

    @Override
    public List<categories> findAll() {

        return categoriesRepositorie.findAll();
    }
}
