package com.tfm.tfm.response;

public class SubcategoryResponse {
	
	private String name;
	private String category;

	public SubcategoryResponse() {}
	
	public SubcategoryResponse(String name) {
		this.name = name;
	}
	
	public SubcategoryResponse(String name, String category) {
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
