package com.tfm.tfm.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CategorySubcategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="category", example="ropa")
	@NotNull
	private String category;
	
	@Schema(name="subcategory", example="deporte")
	@NotNull
	private String subcategory;

	public CategorySubcategoryDto() {}
	
	public CategorySubcategoryDto(@NotNull String category, @NotNull String subcategory) {
		this.category = category;
		this.subcategory = subcategory;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

}
