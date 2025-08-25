package com.tfm.tfm.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tfm.tfm.entity.AutonomousCommunityEntity;
import com.tfm.tfm.entity.BrandEntity;
import com.tfm.tfm.entity.LocationEntity;
import com.tfm.tfm.entity.ProvinceEntity;
import com.tfm.tfm.entity.SubcategoryEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class BrandSpecifications {

    public static Specification<BrandEntity> isCrueltyFree(Boolean crueltyFree) {
        return (root, query, criteriaBuilder) -> 
            crueltyFree != null ? criteriaBuilder.equal(root.get("crueltyFree"), crueltyFree) : null;
    }

    public static Specification<BrandEntity> isVegan(Boolean vegan) {
        return (root, query, criteriaBuilder) -> 
            vegan != null ? criteriaBuilder.equal(root.get("vegan"), vegan) : null;
    }

    public static Specification<BrandEntity> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> 
            category != null ? criteriaBuilder.equal(root.get("categories").get("name"), category) : null;
    }

    public static Specification<BrandEntity> hasSubcategory(String category, String subcategory) {
        return (root, query, criteriaBuilder) -> {
            if (subcategory == null || category == null) {
                return criteriaBuilder.conjunction();
            }

            Join<BrandEntity, SubcategoryEntity> subcategoryJoin = root.join("subcategories", JoinType.INNER);
            
            Predicate subcategoryPredicate = criteriaBuilder.equal(subcategoryJoin.get("name"), subcategory);

            Predicate categoryPredicate = criteriaBuilder.equal(subcategoryJoin.get("category").get("name"), category);

            return criteriaBuilder.and(subcategoryPredicate, categoryPredicate);
        };
    }

    public static Specification<BrandEntity> hasConsumer(String consumer) {
        return (root, query, criteriaBuilder) -> 
            consumer != null ? criteriaBuilder.equal(root.get("consumers").get("type"), consumer) : null;
    }

    public static Specification<BrandEntity> hasPriceRange(Integer price) {
        return (root, query, criteriaBuilder) -> 
            (price != null) ? criteriaBuilder.equal(root.get("price").get("priceRange"), price) : null;
    }

    public static Specification<BrandEntity> isInAutonomousCommunity(String autonomousCommunity) {
        return (root, query, criteriaBuilder) -> {
            if (autonomousCommunity != null) {
                Join<BrandEntity, LocationEntity> locationJoin = root.join("locations", JoinType.LEFT);
                
                Join<LocationEntity, ProvinceEntity> provinceJoin = locationJoin.join("province", JoinType.LEFT);
                
                Join<ProvinceEntity, AutonomousCommunityEntity> communityJoin = provinceJoin.join("autonomousCommunity", JoinType.LEFT);
                
                return criteriaBuilder.equal(communityJoin.get("name"), autonomousCommunity);
            }
            return null;
        };
    }

    public static Specification<BrandEntity> isInProvince(String province) {
        return (root, query, criteriaBuilder) -> {
            if (province != null) {
                Join<BrandEntity, LocationEntity> locationJoin = root.join("locations", JoinType.LEFT);
                
                Join<LocationEntity, ProvinceEntity> provinceJoin = locationJoin.join("province", JoinType.LEFT);
                
                return criteriaBuilder.equal(provinceJoin.get("name"), province);
            }
            return null;
        };
    }

    public static Specification<BrandEntity> isInLocation(String location) {
        return (root, query, criteriaBuilder) -> 
            location != null ? criteriaBuilder.equal(root.get("locations").get("name"), location) : null;
    }

    public static Specification<BrandEntity> hasLabels(List<String> labels) {
        return (root, query, criteriaBuilder) -> {
            if (labels == null || labels.isEmpty()) {
                return null;
            }
            Join<Object, Object> labelsJoin = root.join("labels", JoinType.LEFT);
            return labelsJoin.get("name").in(labels);
        };
    }
}
