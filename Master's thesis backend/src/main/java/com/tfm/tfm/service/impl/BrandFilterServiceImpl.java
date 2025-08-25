package com.tfm.tfm.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.ProvinceEntity;
import com.tfm.tfm.repository.BrandRepository;
import com.tfm.tfm.response.BrandResponse;
import com.tfm.tfm.service.AutonomousCommunityService;
import com.tfm.tfm.service.BrandFilterService;
import com.tfm.tfm.service.BrandService;
import com.tfm.tfm.service.CategoryService;
import com.tfm.tfm.service.ConsumerService;
import com.tfm.tfm.service.LabelService;
import com.tfm.tfm.service.LocationService;
import com.tfm.tfm.service.PriceService;
import com.tfm.tfm.service.ProvinceService;
import com.tfm.tfm.service.SubcategoryService;
import com.tfm.tfm.specification.BrandSpecifications;

@Service
public class BrandFilterServiceImpl implements BrandFilterService{

	@Autowired private BrandRepository brandRepository;

	@Autowired private BrandService brandService;
	@Autowired private CategoryService categoryService;
	@Autowired private SubcategoryService subcategoryService;
	@Autowired private LabelService labelService;
	@Autowired private ConsumerService consumerService;
	@Autowired private PriceService priceService;
	@Autowired private LocationService locationService;
	@Autowired private ProvinceService provinceService;
	@Autowired private AutonomousCommunityService autonomousCommunityService;

  public List<BrandResponse> getBrandsByCategory(String category){
    List<BrandEntity> brandEntityList = categoryService.getBrandsByCategory(category);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsBySubcategory(String subcategory, String category){
    List<BrandEntity> brandEntityList = subcategoryService.getBrandsBySubcategory(subcategory, category);
    return getBrandResponseList(brandEntityList);
  }

  public	List<BrandResponse> getBrandsByIsVegan(boolean isVegan){
    List<BrandEntity> brandEntityList = brandService.getBrandListIsVegan(isVegan);
    return getBrandResponseList(brandEntityList);
  }

  public	List<BrandResponse> getBrandsByIsCrueltyFree(boolean isCrueltyFree){
    List<BrandEntity> brandEntityList = brandService.getBrandListIsCrueltyFree(isCrueltyFree);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByLabel(String label) {
    List<BrandEntity> brandEntityList = labelService.getBrandsByLabel(label);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByConsumer(String consumer){
    List<BrandEntity> brandEntityList = consumerService.getBrandsByConsumer(consumer);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByPrice(Integer price){
    List<BrandEntity> brandEntityList = priceService.getBrandsByPrice(price);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByLocation(String location){
    List<BrandEntity> brandEntityList = locationService.getBrandsByLocation(location);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByProvince(String province){
    List<BrandEntity> brandEntityList = provinceService.getBrandsByProvince(province);
    return getBrandResponseList(brandEntityList);
  }

  public List<BrandResponse> getBrandsByAutonomousCommunity(String autonomousCommunity){
    
    List<ProvinceEntity> provinces = autonomousCommunityService.getProvincesByAutonomousCommunity(autonomousCommunity);

    Set<BrandEntity> brands = new HashSet<>();

    for (ProvinceEntity province : provinces) {
      brands.addAll(provinceService.getBrandsByProvince(province));
    }

    if(brands == null) return null;

    return getBrandResponseList(brands.stream().collect(Collectors.toList()));
  }

  private List<BrandResponse> getBrandResponseList(List<BrandEntity> brandEntityList){
    return brandEntityList
      .stream()
      .map(entity -> brandService.getBrandResponse(entity))
      .collect(Collectors.toList());
  }

  public List<BrandResponse> getFilteredBrands(String category, String subcategory, Boolean vegan, Boolean crueltyFree,
                                               Integer price, String location, 
                                               String autonomousCommunity, String province, List<String> labels, String consumer) {

    Specification<BrandEntity> spec = Specification.where(null);

    if (category != null && subcategory == null) {
        spec = spec.and(BrandSpecifications.hasCategory(category));
    }
    if (category != null && subcategory != null) {
        spec = spec.and(BrandSpecifications.hasSubcategory(category, subcategory));
    } 
    if (vegan != null) {
        spec = spec.and(BrandSpecifications.isVegan(vegan));
    }
    if (crueltyFree != null) {
        spec = spec.and(BrandSpecifications.isCrueltyFree(crueltyFree));
    }
    if (price != null) {
        spec = spec.and(BrandSpecifications.hasPriceRange(price));
    }
    if (location != null) {
        spec = spec.and(BrandSpecifications.isInLocation(location));
    }
    if (autonomousCommunity != null) {
        spec = spec.and(BrandSpecifications.isInAutonomousCommunity(autonomousCommunity));
    }
    if (province != null) {
        spec = spec.and(BrandSpecifications.isInProvince(province));
    }
    if (labels != null && !labels.isEmpty()) {
        spec = spec.and(BrandSpecifications.hasLabels(labels));
    }
    if (consumer != null) {
        spec = spec.and(BrandSpecifications.hasConsumer(consumer));
    }

    return brandRepository.findAll(spec)
      .stream()
      .map(brand -> brandService.getBrandResponse(brand))
      .collect(Collectors.toList());
  }
  
}
