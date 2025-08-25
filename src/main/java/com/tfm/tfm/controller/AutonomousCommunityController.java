package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.response.AutonomousCommunityResponse;
import com.tfm.tfm.response.ProvinceResponse;
import com.tfm.tfm.service.AutonomousCommunityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class AutonomousCommunityController {
	@Autowired private AutonomousCommunityService autonomousCommunityService;

	@GetMapping("/autonomousCommunities")
	@Operation(summary = "Get all autonomous Communities", 
    description = "Get all autonomous Communities in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<AutonomousCommunityResponse> getAllAutonomousCommunity() {
		return autonomousCommunityService.getAutonomousCommunityList();
	}
	
	@GetMapping("/autonomousCommunities/with-brands")
	@Operation(summary = "Get all autonomous Communities with brand")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<AutonomousCommunityResponse> getAllWithBrand() {
		return autonomousCommunityService.getAutonomousCommunitiesWithBrand();
	}
}
