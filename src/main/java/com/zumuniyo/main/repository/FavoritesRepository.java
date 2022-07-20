package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.FavoritesDTO;
import com.zumuniyo.main.dto.FavoritesKey;

public interface FavoritesRepository extends QuerydslPredicateExecutor<FavoritesDTO>,PagingAndSortingRepository<FavoritesDTO, FavoritesKey>{

}
