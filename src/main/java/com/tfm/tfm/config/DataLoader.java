package com.tfm.tfm.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.tfm.tfm.entity.AutonomousCommunityEntity;
import com.tfm.tfm.entity.PriceEntity;
import com.tfm.tfm.entity.ProvinceEntity;
import com.tfm.tfm.repository.AutonomousCommunityRepository;
import com.tfm.tfm.repository.PriceRepository;
import com.tfm.tfm.repository.ProvinceRepository;
import com.tfm.tfm.response.GeoApiAutonomousCommunityResponse;
import com.tfm.tfm.response.GeoApiProvinceResponse;
import com.tfm.tfm.service.AutonomousCommunityService;

@Component
public class DataLoader {

	@Autowired private AutonomousCommunityService autonomousCommunityService;

 	private RestTemplate restTemplate;

	public DataLoader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
  }

	@Bean
	CommandLineRunner initDatabase (PriceRepository priceRepository, AutonomousCommunityRepository autonomousCommunityRepository, ProvinceRepository provinceRepository){
		return args -> {

			if(priceRepository.count() == 0){
				priceRepository.save(new PriceEntity(1));
				priceRepository.save(new PriceEntity(2));
				priceRepository.save(new PriceEntity(3));
				priceRepository.save(new PriceEntity(4));
			}
        
			if (autonomousCommunityRepository.count() == 0) {
				String url = "https://apiv1.geoapi.es/comunidades?&key=9efacab313037b4efce2bc81020e658e8c6e6c7661d3f6b145c37064de2b43fc&type=JSON";
				GeoApiAutonomousCommunityResponse response = restTemplate.getForObject(url, GeoApiAutonomousCommunityResponse.class);

		    if (response == null || response.getData() == null || response.getData().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is any AC.");
				List<AutonomousCommunityEntity> communities = response.getData()
					.stream()
					.map(apiCommunity -> new AutonomousCommunityEntity(apiCommunity.getCode(), apiCommunity.getName()))
					.collect(Collectors.toList());

				autonomousCommunityRepository.saveAll(communities);
			};

			if (provinceRepository.count() == 0) {
				
				List<AutonomousCommunityEntity> autonomousCommunityEntityList = autonomousCommunityService.getAutonomousCommunityEntityList();

				autonomousCommunityEntityList.forEach(entity -> {
						String url = "https://apiv1.geoapi.es/provincias?CCOM="+ entity.getId() +"&key=9efacab313037b4efce2bc81020e658e8c6e6c7661d3f6b145c37064de2b43fc&type=JSON";
						GeoApiProvinceResponse response = restTemplate.getForObject(url, GeoApiProvinceResponse.class);
						
						if (response == null || response.getData() == null || response.getData().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is any AC.");
						List<ProvinceEntity> provinces = response.getData()
							.stream()
							.map(apiProvince -> new ProvinceEntity(apiProvince.getCode(), apiProvince.getName(), entity))
							.collect(Collectors.toList());

						provinceRepository.saveAll(provinces);

				});
			}
		};
	}
}
