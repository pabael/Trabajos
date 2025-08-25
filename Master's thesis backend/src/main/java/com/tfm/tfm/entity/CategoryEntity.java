package com.tfm.tfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class CategoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;

	@ManyToMany (mappedBy = "categories")
	private List<BrandEntity> brands = new ArrayList<>();
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "category")
	private List<SubcategoryEntity> subcategories = new ArrayList<>();
	
	public CategoryEntity() {}
	
	public CategoryEntity(String name) {
		this.name = name;
	}

	public CategoryEntity(String name, List<SubcategoryEntity> subcategories) {
		this.name = name;
		this.subcategories = subcategories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BrandEntity> getBrands() {
		return brands;
	}

	public void setBrands(List<BrandEntity> brands) {
		this.brands = brands;
	}

	public List<SubcategoryEntity> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<SubcategoryEntity> subcategories) {
		this.subcategories = subcategories;
	}
	
	public void addSubcategory(SubcategoryEntity subcategory) {
		this.subcategories.add(subcategory);
	}
	
	public void deleteSubcategory(SubcategoryEntity subcategory) {
		this.subcategories.remove(subcategory);
	}
}
