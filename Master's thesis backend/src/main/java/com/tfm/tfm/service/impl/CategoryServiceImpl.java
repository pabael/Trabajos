package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.CategoryEntity;
import com.tfm.tfm.entity.SubcategoryEntity;
import com.tfm.tfm.repository.CategoryRepository;
import com.tfm.tfm.response.CategoryResponse;
import com.tfm.tfm.service.CategoryService;
import com.tfm.tfm.service.GeneralService;
import com.tfm.tfm.service.SubcategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired private CategoryRepository categoryRepository;
	
	@Autowired private GeneralService generalService;
	@Autowired private SubcategoryService subcategoryService;

	//Categories
	
	public CategoryResponse createCategory(CategoryDto categoryDto) {
		
		if(categoryRepository.existsByName(categoryDto.getName())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");

		CategoryEntity categoryEntity = new CategoryEntity(generalService.capitalizeFirstLetter(categoryDto.getName()));
		categoryRepository.save(categoryEntity);

		List<SubcategoryEntity> subcategoryEntityList = subcategoryService.createSubcategoriesList(categoryDto);
		categoryEntity.setSubcategories(subcategoryEntityList);
		
		categoryRepository.save(categoryEntity);
		
		return getCategoryResponse(categoryEntity);
		
	}
	
	private CategoryResponse getCategoryResponse(CategoryEntity categoryEntity) {
	
		List<SubcategoryEntity> subcategoriesEntity = categoryEntity.getSubcategories();

		if(subcategoriesEntity == null) return new CategoryResponse(categoryEntity.getName());
		
		List<String> subcategories = subcategoriesEntity.stream().map(SubcategoryEntity::getName).collect(Collectors.toList());
	
		return new CategoryResponse(categoryEntity.getName(), subcategories);
	}

	private CategoryResponse getCategoryResponse(CategoryEntity categoryEntity, List<SubcategoryEntity> subcategoriesEntity) {

		if(subcategoriesEntity == null) return new CategoryResponse(categoryEntity.getName());
		
		List<String> subcategories = subcategoriesEntity.stream().map(SubcategoryEntity::getName).collect(Collectors.toList());
	
		return new CategoryResponse(categoryEntity.getName(), subcategories);
	}
	
	public CategoryEntity getCategoryEntity(String category) {
		
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(category);
		if(categoryEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");

		return categoryEntity.get();
	}

	public List<BrandEntity> getBrandsByCategory(String category){
		
		CategoryEntity categoryEntity = getCategoryEntity(category);
		
		return categoryEntity.getBrands();
	}
	
	public List<CategoryEntity> getListCategoryEntity(List<CategoryDto> categories) {
		
		if(categories == null) return null;
		
		return categories.stream()
    .map(category -> categoryRepository.findByName(category.getName()))
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
	}

	public List<CategoryResponse> getListCategoryResponse(List<CategoryEntity> categories, List<SubcategoryEntity> subcategories) {

		if(categories == null) return null;

		return categories.stream()
			.map(category -> {
				List<SubcategoryEntity> subactegoriesOfCategory = subcategories.stream().filter(subcategory -> subcategory.getCategory() == category).collect(Collectors.toList());
				return getCategoryResponse(category, subactegoriesOfCategory);
			})
			.collect(Collectors.toList());
	}

	public List<CategoryResponse> getAllCategories(){

		return getListCategoryResponse(categoryRepository.findAll());
	}

	
	private List<CategoryResponse> getListCategoryResponse(List<CategoryEntity> categories) {

		if(categories == null) return null;

		return categories.stream()
			.map(category -> getCategoryResponse(category))
			.collect(Collectors.toList());
	}

	public void deleteCategory(String category){
		
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(category);
		
		if(categoryEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");
		
		categoryRepository.delete(categoryEntity.get());
	}

	public 	CategoryResponse getCategory(String category){

		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(category);

		if(categoryEntity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");

		return getCategoryResponse(categoryEntity.get());
	}
}
