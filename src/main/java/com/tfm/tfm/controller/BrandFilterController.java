package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.response.BrandResponse;
import com.tfm.tfm.service.BrandFilterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class BrandFilterController {
	@Autowired private BrandFilterService brandFilterService;

@GetMapping("/brands/filter")
@Operation(summary = "Get brands based on various filters")
	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "${api.response-codes.ok.desc}"),
			@ApiResponse(responseCode = "400", description = "${api.response-codes.badRequest.desc}")
	})
	public List<BrandResponse> getFilteredBrands(
    @RequestParam(required = false) String category,
    @RequestParam(required = false) String subcategory,
    @RequestParam(required = false) Boolean vegan,
    @RequestParam(required = false) Boolean crueltyFree,
    @RequestParam(required = false) Integer price,
    @RequestParam(required = false) String location,
    @RequestParam(required = false) String autonomousCommunity,
    @RequestParam(required = false) String province,
    @RequestParam(required = false) List<String> labels,
    @RequestParam(required = false) String consumer
) {
    return brandFilterService.getFilteredBrands(
        category, subcategory, vegan, crueltyFree, price, location, autonomousCommunity, province, labels, consumer
    );
}

	@GetMapping("/brands/category")
	@Operation(summary = "Get all brands from a category")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByCategory(@RequestParam @Valid String category) {
		return brandFilterService.getBrandsByCategory(category);
	}

	@GetMapping("/brands/subcategory")
	@Operation(summary = "Get all brands from a subcategory")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getBySubcategory(@RequestParam @Valid String subcategory, String category) {
		return brandFilterService.getBrandsBySubcategory(subcategory, category);
	}

	@GetMapping("/brands/isVegan")
	@Operation(summary = "Get all brands from a isVegan")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByIsVegan(@RequestParam @Valid boolean isVegan) {
		return brandFilterService.getBrandsByIsVegan(isVegan);
	}

	@GetMapping("/brands/isCrueltyFree")
	@Operation(summary = "Get all brands from a isCrueltyFree")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByIsCrueltyFree(@RequestParam @Valid boolean isCrueltyFree) {
		return brandFilterService.getBrandsByIsCrueltyFree(isCrueltyFree);
	}

	@GetMapping("/brands/label")
	@Operation(summary = "Get all brands from label")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByLabel(@RequestParam @Valid String label) {
		return brandFilterService.getBrandsByLabel(label);
	}

	@GetMapping("/brands/consumer")
	@Operation(summary = "Get all brands from consumer")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByConsumer(@RequestParam @Valid String consumer) {
		return brandFilterService.getBrandsByConsumer(consumer);
	}

	@GetMapping("/brands/price")
	@Operation(summary = "Get all brands from price")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByPrice(@RequestParam @Valid Integer price) {
		return brandFilterService.getBrandsByPrice(price);
	}

	@GetMapping("/brands/location")
	@Operation(summary = "Get all brands from location")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByLocation(@RequestParam @Valid String location) {
		return brandFilterService.getBrandsByLocation(location);
	}

	@GetMapping("/brands/province")
	@Operation(summary = "Get all brands from province")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByProvince(@RequestParam @Valid String province) {
		return brandFilterService.getBrandsByProvince(province);
	}

	@GetMapping("/brands/autonomousCommunity")
	@Operation(summary = "Get all brands from autonomousCommunity")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<BrandResponse> getByAutonomousCommunity(@RequestParam @Valid String autonomousCommunity) {
		return brandFilterService.getBrandsByAutonomousCommunity(autonomousCommunity);
	}
}
