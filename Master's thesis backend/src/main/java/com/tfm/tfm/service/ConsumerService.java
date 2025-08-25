package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.ConsumerDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.ConsumerEntity;
import com.tfm.tfm.response.ConsumerResponse;

public interface ConsumerService {
		
	ConsumerResponse createConsumer(ConsumerDto consumerDto);
	void deleteConsumer(ConsumerDto consumerDto);
	List<ConsumerResponse> getAllConsumers();

	List<ConsumerEntity> getListConsumerEntity(List<String> consumers);

	List<BrandEntity> getBrandsByConsumer(String consumer);

}
