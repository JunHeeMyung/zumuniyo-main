package com.zumuniyo.main.repository;

import java.util.Optional;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MemberDTO;

public interface MemberRepository extends QuerydslPredicateExecutor<MemberDTO>,PagingAndSortingRepository<MemberDTO, Long>{
	
	Optional<MemberDTO> findByMemEmailAndSocialType(String memEmail,String socialType);
	
}