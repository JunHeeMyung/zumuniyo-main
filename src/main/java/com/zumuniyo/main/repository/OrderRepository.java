package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.OrderDTO;

public interface OrderRepository extends QuerydslPredicateExecutor<OrderDTO>,PagingAndSortingRepository<OrderDTO, Long>{

}
