package com.tfm.tfm.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class BrandDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="name", example="Clotsy")
	@NotNull
	private String name;
	
	@Schema(name="summary", example="Empresa valenciana.")
	private String summary;
	
	@Schema(name="url", example="https://www.clotsybrand.com")
	private String url;
	
	@Schema(name="materials", example="algodón orgánico")
	private String materials;
	
	@Schema(name="crueltyFree", type="boolean",example="true")
	private Boolean crueltyFree;
	
	@Schema(name="vegan", type="boolean", example="true")
	private Boolean vegan;
	
	@Schema(name="commitment", example="Plantan árboles.")
	private String commitment;
	
	@Schema(name="production", example="Pequeños artesanos. ")
	private String production;
	
	private List<CategoryDto> categories;

	@Schema(name="labels", type="array", example="[\"GOTS\"]")
	private List<String> labels;

	@Schema(name="consumers", type="array", example="[\"Mujer\"]")
	private List<String> consumers;

	@Schema(name="price", example="1")
	private Integer price;

	private List<LocationDto> locations;

	public BrandDto() {}
	
	public BrandDto(@NotNull String name, String summary, String url, String materials, Boolean crueltyFree,
			Boolean vegan, String commitment, String production, List<CategoryDto> categories, 
			List<String> labels, List<String> consumers, Integer price,List<LocationDto> locations) {
		this.name = name;
		this.summary = summary;
		this.url = url;
		this.materials = materials;
		this.crueltyFree = crueltyFree;
		this.vegan = vegan;
		this.commitment = commitment;
		this.production = production;
		this.categories = categories;
		this.labels = labels;
		this.consumers = consumers;
		this.price = price;
		this.locations = locations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public Boolean isCrueltyFree() {
		return crueltyFree;
	}

	public void setCrueltyFree(Boolean crueltyFree) {
		this.crueltyFree = crueltyFree;
	}

	public Boolean isVegan() {
		return vegan;
	}

	public void setVegan(Boolean vegan) {
		this.vegan = vegan;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDto> categories) {
		this.categories = categories;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getConsumers() {
		return consumers;
	}

	public void setConsumers(List<String> consumers) {
		this.consumers = consumers;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<LocationDto> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDto> locations) {
		this.locations = locations;
	}
}
