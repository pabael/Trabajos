package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.LabelEntity;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, String>{
    boolean existsByName(String name);
    
    Optional<LabelEntity> findByName(String name);
}
