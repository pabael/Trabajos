package com.tfm.tfm.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AutonomousCommunityDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(name="name", example="Aragón")
	@NotNull
	private String name;

	public AutonomousCommunityDto() {}
	
	public AutonomousCommunityDto(@NotNull String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
