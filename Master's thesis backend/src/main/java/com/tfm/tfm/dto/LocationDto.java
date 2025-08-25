package com.tfm.tfm.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class LocationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="name", example="Calatayud")
	@NotNull
	private String name;

	@Schema(name="province", example="Zaragoza")
	@NotNull
	private String province;

	public LocationDto() {}
	
	public LocationDto(@NotNull String name, @NotNull String province) {
		this.name = name;
		this.province = province;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
