package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MenuCategoryDTO;

public interface MenuCategoryRepository extends QuerydslPredicateExecutor<MenuCategoryDTO>,PagingAndSortingRepository<MenuCategoryDTO, Long>{

}
