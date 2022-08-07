package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.OrderDTO;
import com.zumuniyo.main.dto.OrderGroupDTO;

public interface OrderRepository extends QuerydslPredicateExecutor<OrderDTO>,PagingAndSortingRepository<OrderDTO, Long>{
	
	List<OrderDTO> findByOrderGroup(OrderGroupDTO orderGroup);

}
