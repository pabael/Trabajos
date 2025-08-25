package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.LocationDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.LocationEntity;
import com.tfm.tfm.response.LocationResponse;

public interface LocationService {
			
	LocationResponse createLocation(LocationDto locationDto);

	List<LocationResponse> getLocationList(String province);

	List<LocationResponse> getListLocationResponse(List<LocationEntity> locations);

	List<LocationEntity> getListLocationEntity(List<LocationDto> locations);

	List<BrandEntity> getBrandsByLocation(String location);

	List<LocationResponse> getLocationsWithBrand();
}
