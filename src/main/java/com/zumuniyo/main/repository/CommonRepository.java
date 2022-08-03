package com.zumuniyo.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.ShopDTO;

public interface CommonRepository extends QuerydslPredicateExecutor<ShopDTO>,PagingAndSortingRepository<ShopDTO, Long>{
	
	@Query(value = "SELECT * FROM SHOP JOIN LOCATION ON(SHOP.LOCATION_LOC_ADDR=LOCATION.LOC_ADDR) WHERE SHOP_STATUS = '활성'",nativeQuery = true)
	public List<Map<String,Object>> getShopMapData();

}