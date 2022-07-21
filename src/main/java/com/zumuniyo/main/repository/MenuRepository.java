package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MenuDTO;

public interface MenuRepository extends QuerydslPredicateExecutor<MenuDTO>,PagingAndSortingRepository<MenuDTO, Long>{

}
