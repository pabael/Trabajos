package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.AutonomousCommunityEntity;

@Repository
public interface AutonomousCommunityRepository extends JpaRepository<AutonomousCommunityEntity, String>{
	
    boolean existsByName(String name);

    Optional<AutonomousCommunityEntity> findByName(String name);

}
