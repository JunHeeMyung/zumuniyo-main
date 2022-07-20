package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.AdvertisementDTO;

public interface AdvertisementRepository extends QuerydslPredicateExecutor<AdvertisementDTO>,PagingAndSortingRepository<AdvertisementDTO, Long>{

}
