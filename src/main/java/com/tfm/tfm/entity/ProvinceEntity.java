package com.tfm.tfm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Province")
public class ProvinceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;

	@OneToMany (mappedBy = "province")
	private List<LocationEntity> locations = new ArrayList<>();

	@ManyToOne
	private AutonomousCommunityEntity autonomousCommunity;
	
	public ProvinceEntity() {}
	
	public ProvinceEntity(String id, String name, AutonomousCommunityEntity autonomousCommunity) {
		this.id = id;
		this.name = name;
		this.autonomousCommunity= autonomousCommunity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LocationEntity> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationEntity> locations) {
		this.locations = locations;
	}
	
	public void addLocation(LocationEntity location) {
		this.locations.add(location);
	}

	public AutonomousCommunityEntity getAutonomousCommunity() {
		return autonomousCommunity;
	}

	public void setAutonomousCommunity(AutonomousCommunityEntity autonomousCommunity) {
		this.autonomousCommunity = autonomousCommunity;
	}
}
