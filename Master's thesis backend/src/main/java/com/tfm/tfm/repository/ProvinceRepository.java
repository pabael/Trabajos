package com.tfm.tfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.AutonomousCommunityEntity;
import com.tfm.tfm.entity.ProvinceEntity;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceEntity, String>{
    boolean existsByName(String name);
    
    Optional<ProvinceEntity> findByName(String name);

    List<ProvinceEntity> findByAutonomousCommunity(AutonomousCommunityEntity autonomousCommunity);

}
