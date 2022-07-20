package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.OrderGroupDTO;

public interface OrderGroupRepository extends QuerydslPredicateExecutor<OrderGroupDTO>,PagingAndSortingRepository<OrderGroupDTO, Long>{

}
