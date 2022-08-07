package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.CouponDTO;
import com.zumuniyo.main.dto.OrderGroupDTO;

public interface CouponRepository extends QuerydslPredicateExecutor<CouponDTO>,PagingAndSortingRepository<CouponDTO, Long>{

	List<CouponDTO> findByOrderGroup(OrderGroupDTO orderGroup);
	
}
