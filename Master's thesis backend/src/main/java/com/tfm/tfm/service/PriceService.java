package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.PriceEntity;
import com.tfm.tfm.response.PriceResponse;

public interface PriceService {

	PriceEntity getPriceEntity(Integer price);
	List<PriceResponse> getAll();

	List<BrandEntity> getBrandsByPrice(Integer price);

}
