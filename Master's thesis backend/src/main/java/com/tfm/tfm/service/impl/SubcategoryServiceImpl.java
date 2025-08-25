package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.dto.SubcategoryDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.CategoryEntity;
import com.tfm.tfm.entity.SubcategoryEntity;
import com.tfm.tfm.repository.CategoryRepository;
import com.tfm.tfm.repository.SubcategoryRepository;
import com.tfm.tfm.response.SubcategoryResponse;
import com.tfm.tfm.service.GeneralService;
import com.tfm.tfm.service.SubcategoryService;

@Service
public class SubcategoryServiceImpl implements SubcategoryService{

	@Autowired private SubcategoryRepository subcategoryRepository;
	@Autowired private CategoryRepository categoryRepository;
	
	@Autowired private GeneralService generalService;
	
	public SubcategoryResponse createSubcategory(SubcategoryDto subcategoryDto) {

		if(subcategoryRepository.existsByNameAndCategory_Name(subcategoryDto.getName(), subcategoryDto.getCategory())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory already exists");

		SubcategoryEntity subcategoryEntity = getNewSubcategoryEntity(subcategoryDto);

		subcategoryRepository.save(subcategoryEntity);
		
		return getSubcategoryResponse(subcategoryEntity);
		
	}

	private SubcategoryEntity getNewSubcategoryEntity (SubcategoryDto subcategoryDto){

		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(subcategoryDto.getCategory());
		if(categoryEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");

		return new SubcategoryEntity(generalService.capitalizeFirstLetter(subcategoryDto.getName()), categoryEntity.get());
	}

	private SubcategoryEntity getNewSubcategoryEntity (String subcategory, String category){

		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(category);
		if(categoryEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category does not exist");

		return new SubcategoryEntity(generalService.capitalizeFirstLetter(subcategory), categoryEntity.get());
	}
	
	private SubcategoryResponse getSubcategoryResponse(SubcategoryEntity subcategoryEntity) {
		
		String category = subcategoryEntity.getCategory().getName();

		return new SubcategoryResponse(subcategoryEntity.getName(), category);
	}

	public	List<SubcategoryEntity> createSubcategoriesList (CategoryDto category){

		List<String> subcategories = category.getSubcategories();

		if(subcategories == null) return null;

		return subcategories.stream()
			.map(subcategoryName -> getNewSubcategoryEntity(subcategoryName, category.getName()))
			.collect(Collectors.toList());
	}

	
	public List<SubcategoryEntity> getListSubcategoryEntity(List<CategoryDto> categories) {
		
		if(categories == null) return null;

		return categories.stream()
			.flatMap(category -> category.getSubcategories().stream()
					.map(subcategoryName -> subcategoryRepository.findByNameAndCategory_Name(subcategoryName, category.getName()))
					.filter(Optional::isPresent)
					.map(Optional::get))
			.collect(Collectors.toList());
	}

	public void deleteSubcategory(SubcategoryDto subcategoryDto){
		
		Optional<SubcategoryEntity> subcategory = subcategoryRepository.findByNameAndCategory_Name(subcategoryDto.getName(), subcategoryDto.getCategory());
		
		if(subcategory.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory does not exist");

		CategoryEntity categoryEntity = subcategory.get().getCategory();
		categoryEntity.deleteSubcategory(subcategory.get());
		categoryRepository.save(categoryEntity);

		subcategoryRepository.delete(subcategory.get());
	}

	public List<BrandEntity> getBrandsBySubcategory(String subcategory, String category){
		
		Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findByNameAndCategory_Name(subcategory, category);
		if(subcategoryEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Subcategory does not exist");

		return subcategoryEntity.get().getBrands();
	}
}
