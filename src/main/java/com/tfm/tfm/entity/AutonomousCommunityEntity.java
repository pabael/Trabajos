package com.tfm.tfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "AutonomousCommunity")
public class AutonomousCommunityEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name ;
	 
	@OneToMany (mappedBy = "autonomousCommunity")
	private List<ProvinceEntity> provinces = new ArrayList<>();
	
	public AutonomousCommunityEntity() {}
	
	public AutonomousCommunityEntity(String id, String name) {
		this.id = id;
		this.name  = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name ;
	}

	public void setName(String name ) {
		this.name  = name ;
	}

	public List<ProvinceEntity> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<ProvinceEntity> provinces) {
		this.provinces = provinces;
	}

	public void addProvince(ProvinceEntity province) {
		this.provinces.add(province);
	}
}
