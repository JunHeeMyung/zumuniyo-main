package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.ReviewDTO;

public interface ReviewRepository extends QuerydslPredicateExecutor<ReviewDTO>,PagingAndSortingRepository<ReviewDTO, Long>{

}
