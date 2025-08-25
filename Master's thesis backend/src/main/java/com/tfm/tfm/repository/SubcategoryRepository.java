package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.SubcategoryEntity;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, String>{

    boolean existsByNameAndCategory_Name(String subcategoryName, String categoryName);
    
    Optional<SubcategoryEntity> findByNameAndCategory_Name(String subcategoryName, String categoryName);
}
