package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.response.ProvinceResponse;
import com.tfm.tfm.service.ProvinceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ProvinceController {
	@Autowired private ProvinceService provinceService;

	@GetMapping("/provinces")
	@Operation(summary = "Get all provinces of an autonomous Community")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<ProvinceResponse> getAll(@RequestParam(required = true) String autonomousCommunity) {
		return provinceService.getProvinceList(autonomousCommunity);
	}

	@GetMapping("/provinces/with-brands")
	@Operation(summary = "Get all provinces with brand")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<ProvinceResponse> getAllWithBrand() {
		return provinceService.getProvincesWithBrand();
	}
}
