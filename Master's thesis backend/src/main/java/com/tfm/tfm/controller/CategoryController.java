package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.response.CategoryResponse;
import com.tfm.tfm.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class CategoryController {
	@Autowired private CategoryService categoryService;

	@PostMapping("/category")
	@Operation(summary = "Create new Category", 
    description = "Create a new category in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public CategoryResponse create(@RequestBody @Valid CategoryDto categoryDto) {
		return categoryService.createCategory(categoryDto);
	}

	@DeleteMapping("/category")
	@Operation(summary = "Delete Category information", 
    description = "Delete category giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public void deleteCategory(@RequestParam String category) {
		categoryService.deleteCategory(category);
	}

	@GetMapping("/categories")
	@Operation(summary = "Get all categories")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<CategoryResponse> getAll() {
		return categoryService.getAllCategories();
	}
	
	@GetMapping("/category")
	@Operation(summary = "Get a category with its subcategories")

	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", 
				description = "${api.response-codes.ok.desc}"),
			@ApiResponse(responseCode = "400", 
	            description = "${api.response-codes.badRequest.desc}")
	})
	public CategoryResponse getCategory(@RequestParam String category) {
		return categoryService.getCategory(category);
	}
}
