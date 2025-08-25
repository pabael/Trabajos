package com.tfm.tfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.dto.CategoryDto;
import com.tfm.tfm.dto.CategorySubcategoryDto;
import com.tfm.tfm.dto.SubcategoryDto;
import com.tfm.tfm.response.CategoryResponse;
import com.tfm.tfm.response.SubcategoryResponse;
import com.tfm.tfm.service.SubcategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class SubcategoryController {
	@Autowired private SubcategoryService subcategoryService;
	
	@PostMapping("/subcategory")
	@Operation(summary = "Create new Subcategory", 
    description = "Create a new subcategory in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public SubcategoryResponse create(@RequestBody @Valid SubcategoryDto subcategoryDto) {
		return subcategoryService.createSubcategory(subcategoryDto);
	}

	@DeleteMapping("/subcategory")
	@Operation(summary = "Delete subcategory information", 
    description = "Delete subcategory giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public void deleteSuybcategory(@RequestBody @Valid SubcategoryDto subcategoryDto) {
		subcategoryService.deleteSubcategory(subcategoryDto);
	}
}
