package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.CouponDTO;

public interface CouponRepository extends QuerydslPredicateExecutor<CouponDTO>,PagingAndSortingRepository<CouponDTO, Long>{

}
