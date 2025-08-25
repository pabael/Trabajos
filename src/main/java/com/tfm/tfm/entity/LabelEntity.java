package com.tfm.tfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Label")
public class LabelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;

	@ManyToMany (mappedBy = "labels")
	private List<BrandEntity> brands = new ArrayList<>();
	
	public LabelEntity() {}
	
	public LabelEntity(String name) {
		this.name = name;
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
}
