package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.dto.ConsumerDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.ConsumerEntity;
import com.tfm.tfm.repository.ConsumerRepository;
import com.tfm.tfm.response.ConsumerResponse;
import com.tfm.tfm.service.ConsumerService;
import com.tfm.tfm.service.GeneralService;


@Service
public class ConsumerServiceImpl implements ConsumerService{

	@Autowired private ConsumerRepository consumerRepository;

	@Autowired private GeneralService generalService;

	public ConsumerResponse createConsumer(ConsumerDto consumerDto) {

		if(!consumerRepository.findByType(consumerDto.getType()).isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consumer already exists");
		
		ConsumerEntity consumerEntity = new ConsumerEntity(generalService.capitalizeFirstLetter(consumerDto.getType()));
		
		consumerRepository.save(consumerEntity);
		
		return new ConsumerResponse(consumerEntity.getType());
		
	}
	
	public List<ConsumerEntity> getListConsumerEntity(List<String> consumers) {

		if(consumers == null) return null;

		return consumers.stream()
    .map(consumer -> consumerRepository.findByType(consumer))
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
	}

	public	List<ConsumerResponse> getAllConsumers(){

		List<ConsumerEntity> entityList = consumerRepository.findAll();

		if(entityList == null) return null;

		return entityList.stream()
			.map(entity -> new ConsumerResponse(entity.getType()))
			.collect(Collectors.toList());
	}


	public	void deleteConsumer(ConsumerDto consumerDto){
	
		Optional<ConsumerEntity> consumer = consumerRepository.findByType(consumerDto.getType());
		
		if(consumer.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consumer does not exist");
		
		consumerRepository.delete(consumer.get());
	}

	public	List<BrandEntity> getBrandsByConsumer(String consumer){
		
		Optional<ConsumerEntity> consumerEntity = consumerRepository.findByType(consumer);

		if(consumerEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consumer does not exist");

		return consumerEntity.get().getBrands();
	}
}
