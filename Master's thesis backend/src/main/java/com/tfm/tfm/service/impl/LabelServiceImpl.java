package com.tfm.tfm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.dto.LabelDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.LabelEntity;
import com.tfm.tfm.repository.LabelRepository;
import com.tfm.tfm.response.LabelResponse;
import com.tfm.tfm.service.LabelService;

@Service
public class LabelServiceImpl implements LabelService{

	@Autowired private LabelRepository labelRepository;


	public LabelResponse createLabel(LabelDto labelDto) {
		
		if(labelRepository.findByName(labelDto.getName()).isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label already exists");

		LabelEntity labelEntity = new LabelEntity(labelDto.getName());
		
		labelRepository.save(labelEntity);
		
		return new LabelResponse(labelEntity.getName());
	}
	
	public List<LabelEntity> getListLabelEntity(List<String> labels) {

		if(labels == null) return null;

		return labels.stream()
    .map(label -> labelRepository.findByName(label))
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());
	}

	public List<LabelResponse> getAllLabels(){

		List<LabelEntity> entityList = labelRepository.findAll();

		if(entityList == null) return null;

		return entityList.stream()
			.map(entity -> new LabelResponse(entity.getName()))
			.collect(Collectors.toList());
	}

	public	void deleteLabel(LabelDto labelDto){
	
		Optional<LabelEntity> label = labelRepository.findByName(labelDto.getName());
		
		if(label.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label does not exist");
		
		labelRepository.delete(label.get());
	}

	public 	List<BrandEntity> getBrandsByLabel(String label){
		
		Optional<LabelEntity> labelEntity = labelRepository.findByName(label);

		if(labelEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label does not exist");

		return labelEntity.get().getBrands();
	}

}
