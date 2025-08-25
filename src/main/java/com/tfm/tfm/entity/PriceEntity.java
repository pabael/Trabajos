package com.tfm.tfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Price")
public class PriceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Integer priceRange ;

	@OneToMany (mappedBy = "price")
	private List<BrandEntity> brands = new ArrayList<>();
	
	public PriceEntity() {}
	
	public PriceEntity(Integer priceRange ) {
		this.priceRange  = priceRange ;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPriceRange() {
		return priceRange ;
	}

	public void setPriceRange(Integer priceRange ) {
		this.priceRange  = priceRange ;
	}

	public List<BrandEntity> getBrands() {
		return brands;
	}

	public void setBrands(List<BrandEntity> brands) {
		this.brands = brands;
	}
}
