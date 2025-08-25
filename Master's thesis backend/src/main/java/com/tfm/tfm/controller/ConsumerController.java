package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.dto.ConsumerDto;
import com.tfm.tfm.response.ConsumerResponse;
import com.tfm.tfm.service.ConsumerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class ConsumerController {
	@Autowired private ConsumerService consumerService;

	@PostMapping("/consumer")
	@Operation(summary = "Create new consumer", 
    description = "Create a new consumer in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public ConsumerResponse create(@RequestBody @Valid ConsumerDto consumerDto) {
		return consumerService.createConsumer(consumerDto);
	}

	@DeleteMapping("/consumer")
	@Operation(summary = "Delete consumer information", 
    description = "Delete consumer giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public void delete(@RequestBody @Valid ConsumerDto consumerDto) {
		consumerService.deleteConsumer(consumerDto);
	}

	@GetMapping("/consumers")
	@Operation(summary = "Get all consumers")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<ConsumerResponse> getAll() {
		return consumerService.getAllConsumers();
	}
}
