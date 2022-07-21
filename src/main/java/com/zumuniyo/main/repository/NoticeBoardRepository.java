package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.NoticeBoardDTO;

public interface NoticeBoardRepository extends QuerydslPredicateExecutor<NoticeBoardDTO>,PagingAndSortingRepository<NoticeBoardDTO, Long>{

}
