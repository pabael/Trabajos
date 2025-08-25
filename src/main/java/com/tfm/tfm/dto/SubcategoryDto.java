package com.tfm.tfm.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class SubcategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="name", example="Pijamas")
	@NotNull
	private String name;
	
	@Schema(name="category", example="Ropa")
	@NotNull
	private String category;

	public SubcategoryDto() {}
	
	public SubcategoryDto(@NotNull String name, @NotNull String category) {
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
