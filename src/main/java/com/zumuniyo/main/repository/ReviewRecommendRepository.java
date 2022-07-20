package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.ReviewRecommendDTO;
import com.zumuniyo.main.dto.ReviewRecommendKey;

public interface ReviewRecommendRepository extends QuerydslPredicateExecutor<ReviewRecommendDTO>,PagingAndSortingRepository<ReviewRecommendDTO, ReviewRecommendKey>{

}
