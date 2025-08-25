package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.CategoryEntity;
import com.tfm.tfm.entity.SubcategoryEntity;
import com.tfm.tfm.response.CategoryResponse;

public interface CategoryService {
		
	CategoryResponse createCategory(CategoryDto categoryDto);
	void deleteCategory(String category);
	List<CategoryResponse> getAllCategories();
	CategoryResponse getCategory(String category);

	CategoryEntity getCategoryEntity(String category);

	List<CategoryEntity> getListCategoryEntity(List<CategoryDto> categories);
	List<CategoryResponse> getListCategoryResponse(List<CategoryEntity> categories, List<SubcategoryEntity> subcategories);

	List<BrandEntity> getBrandsByCategory(String category);
}
