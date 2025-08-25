package com.tfm.tfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, String>, JpaSpecificationExecutor<BrandEntity>{
	
	Optional<BrandEntity> findByName(String name);

	List<BrandEntity> findByVegan(boolean isVegan);

	List<BrandEntity> findByCrueltyFree(boolean isCrueltyFree);

}