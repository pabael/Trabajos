package com.tfm.tfm.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class ConsumerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(type="type", example="Mujer")
	@NotNull
	private String type;


	public ConsumerDto() {}
	
	public ConsumerDto(@NotNull String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
