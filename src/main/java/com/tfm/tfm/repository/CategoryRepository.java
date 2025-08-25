package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String>{
    
    boolean existsByName(String name);
    
    Optional<CategoryEntity> findByName(String name);

    boolean existsByNameAndSubcategories_Name(String categoryName, String subcategoryName);
}
