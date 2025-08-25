package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.dto.SubcategoryDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.SubcategoryEntity;
import com.tfm.tfm.response.SubcategoryResponse;

public interface SubcategoryService {
		
	SubcategoryResponse createSubcategory(SubcategoryDto subcategoryDto);
	void deleteSubcategory(SubcategoryDto subcategoryDto);

	List<SubcategoryEntity> createSubcategoriesList (CategoryDto subcategoryDto);

	List<SubcategoryEntity> getListSubcategoryEntity(List<CategoryDto> categories);

	List<BrandEntity> getBrandsBySubcategory(String subcategory, String category);

}
