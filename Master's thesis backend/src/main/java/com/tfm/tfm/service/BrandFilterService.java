package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.response.BrandResponse;

public interface BrandFilterService {
		
	List<BrandResponse> getBrandsByCategory(String category);

	List<BrandResponse> getBrandsBySubcategory(String subcategory, String category);

	List<BrandResponse> getBrandsByIsVegan(boolean isVegan);

	List<BrandResponse> getBrandsByIsCrueltyFree(boolean isCrueltyFree);

	List<BrandResponse> getBrandsByLabel(String label);
	
	List<BrandResponse> getBrandsByConsumer(String consumer);

	List<BrandResponse> getBrandsByPrice(Integer price);

	List<BrandResponse> getBrandsByLocation(String location);

	List<BrandResponse> getBrandsByProvince(String province);

	List<BrandResponse> getBrandsByAutonomousCommunity(String autonomousCommunity);

	List<BrandResponse> getFilteredBrands(String category, String subcategory, Boolean vegan, Boolean crueltyFree,
                                               Integer price, String location, 
                                               String autonomousCommunity, String province, List<String> labels, String consumer);
}
