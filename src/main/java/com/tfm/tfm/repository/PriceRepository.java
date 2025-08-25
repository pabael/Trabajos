package com.tfm.tfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfm.tfm.entity.PriceEntity;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, String>{
	
    Optional<PriceEntity> findByPriceRange(Integer price);

}
