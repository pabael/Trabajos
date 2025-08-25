package com.tfm.tfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tfm.tfm.dto.LabelDto;
import com.tfm.tfm.response.LabelResponse;
import com.tfm.tfm.service.LabelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class LabelController {
	@Autowired private LabelService labelService;

	@PostMapping("/label")
	@Operation(summary = "Create new label", 
    description = "Create a new label in the system.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public LabelResponse create(@RequestBody @Valid LabelDto labelDto) {
		return labelService.createLabel(labelDto);
	}

	@DeleteMapping("/label")
	@Operation(summary = "Delete label information", 
    description = "Delete label giving Name.")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public void delete(@RequestBody @Valid LabelDto labelDto) {
		labelService.deleteLabel(labelDto);
	}

	@GetMapping("/labels")
	@Operation(summary = "Get all labels")

		@ApiResponses(value = { 
				@ApiResponse(responseCode = "200", 
					description = "${api.response-codes.ok.desc}"),
				@ApiResponse(responseCode = "400", 
		            description = "${api.response-codes.badRequest.desc}")
		})
	public List<LabelResponse> getAll() {
		return labelService.getAllLabels();
	}
}
