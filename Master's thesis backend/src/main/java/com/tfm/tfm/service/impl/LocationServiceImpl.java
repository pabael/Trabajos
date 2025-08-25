package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.dto.LocationDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.LocationEntity;
import com.tfm.tfm.entity.ProvinceEntity;
import com.tfm.tfm.repository.LocationRepository;
import com.tfm.tfm.repository.ProvinceRepository;
import com.tfm.tfm.response.GeoApiLocationResponse;
import com.tfm.tfm.response.LocationResponse;
import com.tfm.tfm.service.LocationService;
import com.tfm.tfm.service.ProvinceService;

@Service
public class LocationServiceImpl implements LocationService{

	@Autowired private LocationRepository locationRepository;
	@Autowired private ProvinceRepository provinceRepository;

	@Autowired private ProvinceService provinceService;
	
 	private RestTemplate restTemplate;

	public LocationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
  }

	public LocationResponse createLocation(LocationDto locationDto) {
			
		return getLocationResponse(createLocationGetEntity(locationDto));
	}

	public LocationEntity createLocationGetEntity(LocationDto locationDto){
		Optional<LocationEntity> locationEntity = locationRepository.findByName(locationDto.getName());
		if(locationEntity.isPresent()) return locationEntity.get();

		return getNewLocationEntity(locationDto);
	}

	private LocationEntity getNewLocationEntity(LocationDto locationDto) {
				
		ProvinceEntity provinceEntity = provinceService.getProvinceEntity(locationDto.getProvince());
				
		LocationEntity locationEntity = new LocationEntity(locationDto.getName(), provinceEntity);
				
		provinceEntity.addLocation(locationEntity);
				
		provinceRepository.save(provinceEntity);
		locationRepository.save(locationEntity);
        
		return locationEntity;
	}

	private LocationResponse getLocationResponse(LocationEntity locationEntity){
		return  new LocationResponse(
			locationEntity.getName(), 
			locationEntity.getProvince().getName(), 
			locationEntity.getProvince().getAutonomousCommunity().getName());
	}

	public	List<LocationResponse> getLocationList(String province){

		ProvinceEntity provinceEntity = provinceService.getProvinceEntity(province);

		String url = "https://apiv1.geoapi.es/municipios?CPRO="+ provinceEntity.getId() +"&key=9efacab313037b4efce2bc81020e658e8c6e6c7661d3f6b145c37064de2b43fc&type=JSON";
		GeoApiLocationResponse response = restTemplate.getForObject(url, GeoApiLocationResponse.class);

		if (response == null || response.getData() == null || response.getData().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is any location.");

		return response.getData()
							.stream()
							.map(apiLocation -> new LocationResponse(apiLocation.getName()))
							.collect(Collectors.toList());
	}

	public List<LocationResponse> getListLocationResponse(List<LocationEntity> locations){
		if(locations == null) return null;

		return locations
			.stream()
			.map(entity -> getLocationResponse(entity))
			.collect(Collectors.toList());
	}

	public List<LocationEntity> getListLocationEntity(List<LocationDto> locations) {

		if(locations == null) return null;

		return locations.stream()
			.map(location -> createLocationGetEntity(location))
			.collect(Collectors.toList());
	}

	public List<BrandEntity> getBrandsByLocation(String location){
		
		Optional<LocationEntity> locationEntity = locationRepository.findByName(location);

		if(locationEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location does not exist");

		return locationEntity.get().getBrands();
	}

	public	List<LocationResponse> getLocationsWithBrand(){
		return getListLocationResponse(locationRepository.findAll());
	}


}
