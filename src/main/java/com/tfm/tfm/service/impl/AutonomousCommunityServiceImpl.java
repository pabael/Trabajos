package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.entity.AutonomousCommunityEntity;
import com.tfm.tfm.entity.ProvinceEntity;
import com.tfm.tfm.repository.AutonomousCommunityRepository;
import com.tfm.tfm.repository.LocationRepository;
import com.tfm.tfm.response.AutonomousCommunityResponse;
import com.tfm.tfm.response.ProvinceResponse;
import com.tfm.tfm.service.AutonomousCommunityService;

@Service
public class AutonomousCommunityServiceImpl implements AutonomousCommunityService{

	@Autowired private AutonomousCommunityRepository autonomousCommunityRepository;
	@Autowired private LocationRepository locationRepository;

	public List<AutonomousCommunityResponse> getAutonomousCommunityList(){
		
		List<AutonomousCommunityEntity> listEntity = autonomousCommunityRepository.findAll();

		if(listEntity == null) return null;

		return listEntity.stream()
			.map(entity -> new AutonomousCommunityResponse(entity.getName()))
			.collect(Collectors.toList()); 
	}

	public List<AutonomousCommunityEntity> getAutonomousCommunityEntityList (){
		return autonomousCommunityRepository.findAll();
	}

	public AutonomousCommunityEntity getAutonomousCommunityEntity(String name) {
		
		Optional <AutonomousCommunityEntity> autonomousCommunityEntity = autonomousCommunityRepository.findByName(name);

		//This should not happend
		if(autonomousCommunityEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Autonomous Community dos not exist");
		
		return autonomousCommunityEntity.get();
	}

	public List<ProvinceEntity> getProvincesByAutonomousCommunity(String autonomousCommunity){
		Optional<AutonomousCommunityEntity> autonomousCommunityEntity = autonomousCommunityRepository.findByName(autonomousCommunity);

		if(autonomousCommunityEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No brands");
		
		return autonomousCommunityEntity.get().getProvinces();
		
	}

	public List<AutonomousCommunityResponse> getAutonomousCommunitiesWithBrand(){
		return locationRepository.findAll().stream()
		.map(location -> new AutonomousCommunityResponse(location.getProvince().getAutonomousCommunity().getName()))
		.distinct()
		.collect(Collectors.toList());
	}

}
