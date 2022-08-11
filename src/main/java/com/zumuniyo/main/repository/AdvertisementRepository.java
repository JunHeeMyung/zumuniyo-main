package com.zumuniyo.main.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.zumuniyo.main.dto.AdvertisementDTO;
import com.zumuniyo.main.dto.QAdvertisementDTO;
import com.zumuniyo.main.dto.QNoticeBoardDTO;

public interface AdvertisementRepository extends QuerydslPredicateExecutor<AdvertisementDTO>,PagingAndSortingRepository<AdvertisementDTO, Long>{
	  
	
	public default Predicate makePredicate(String type, String keyword) {
			 BooleanBuilder builder = new BooleanBuilder();
			 QAdvertisementDTO Aboard = QAdvertisementDTO.advertisementDTO;
			 builder.and(Aboard.adSeq.gt(0));
			 //검색조건처리
			 if(type==null) return builder;
			 switch (type) {
			 case "owner": builder.and(Aboard.owner.like("%" + keyword + "%")); break;
			 default: break;
			 }
			 return builder;
			 }
}
