package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.LocationDTO;

public interface LocationRepository extends QuerydslPredicateExecutor<LocationDTO>,PagingAndSortingRepository<LocationDTO, String>{

}
