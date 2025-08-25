package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.ConsumerEntity;

@Repository
public interface ConsumerRepository extends JpaRepository<ConsumerEntity, String>{
    boolean existsByType(String type);
    
    Optional<ConsumerEntity> findByType(String type);
}