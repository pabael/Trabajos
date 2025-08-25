package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.BrandDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.response.BrandResponse;

public interface BrandService {
		
	BrandResponse createBrand(BrandDto brandDto);

	BrandResponse updateBrand(BrandDto brandDto);

	void deleteBrand(String brandName);

	BrandResponse getBrand(String brandName);

	List<BrandResponse> getAllBrands();

	BrandResponse getBrandResponse(BrandEntity brandEntity);
	
	List<BrandEntity> getBrandListIsVegan(boolean isVegan);

	List<BrandEntity> getBrandListIsCrueltyFree(boolean isCrueltyFreevegan);

}
