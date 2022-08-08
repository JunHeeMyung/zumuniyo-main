package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.ShopDTO;

public interface ShopRepository extends QuerydslPredicateExecutor<ShopDTO>,PagingAndSortingRepository<ShopDTO, Long>{
	
	List<ShopDTO> findByMemberOrderByShopSeqDesc(MemberDTO member);

}
