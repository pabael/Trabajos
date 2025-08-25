package com.tfm.tfm.service;

import java.util.List;

import com.tfm.tfm.dto.LabelDto;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.LabelEntity;
import com.tfm.tfm.response.LabelResponse;

public interface LabelService {
		
	LabelResponse createLabel(LabelDto labelDto);
	void deleteLabel(LabelDto labelDto);
	List<LabelResponse> getAllLabels();

	List<LabelEntity> getListLabelEntity(List<String> labels);

	List<BrandEntity> getBrandsByLabel(String label);
}
