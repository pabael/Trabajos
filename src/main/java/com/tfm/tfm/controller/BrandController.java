package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.dto.BrandDto;
import com.tfm.tfm.response.BrandResponse;
import com.tfm.tfm.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class BrandController {
	@Autowired private BrandService brandService;

	@PostMapping("/brand")
	@Operation(summary = "Create new Brand", 
    description = "Create a new brand in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public BrandResponse create(@RequestBody @Valid BrandDto brandDto) {
		return brandService.createBrand(brandDto);
	}
	
	@PutMapping("/brand")
	@Operation(summary = "Update Brand information", 
    description = "Updated brand information giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public BrandResponse update(@RequestBody @Valid BrandDto brandDto) {
		return brandService.updateBrand(brandDto);
	}

	@DeleteMapping("/brand")
	@Operation(summary = "Delete Brand information", 
    description = "Delete brand giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public void delete(@RequestParam String brandName) {
		brandService.deleteBrand(brandName);
	}

	@GetMapping("/brand")
	@Operation(summary = "Get brand information")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public BrandResponse get(@RequestParam String brand) {
		return brandService.getBrand(brand);
	}

	@GetMapping("/brands")
	@Operation(summary = "Get all brands")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getAll() {
		return brandService.getAllBrands();
	}
}
