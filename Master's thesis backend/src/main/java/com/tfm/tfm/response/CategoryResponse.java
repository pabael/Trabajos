package com.tfm.tfm.response;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
	
	private String name;
	private List<String> subcategories = new ArrayList<>();

	public CategoryResponse() {}
	
	public CategoryResponse(String name) {
		this.name = name;
	}
	
	public CategoryResponse(String name, List<String> subcategories) {
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
