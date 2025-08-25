package com.tfm.tfm.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="name", example="Ropa")
	@NotNull
	private String name;

	@Schema(name="subcategories", type="array", example="[\"Deporte\"]")
	private List<String> subcategories;
	
	public CategoryDto() {}
	
	public CategoryDto(@NotNull String name, List<String> subcategories) {
		this.name = name;
		this.subcategories = subcategories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<String> subcategories) {
		this.subcategories = subcategories;
	}
}
