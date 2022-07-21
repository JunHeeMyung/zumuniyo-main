package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.ShopDTO;

public interface ShopRepository extends QuerydslPredicateExecutor<ShopDTO>,PagingAndSortingRepository<ShopDTO, Long>{

}
