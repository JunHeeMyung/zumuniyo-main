package com.zumuniyo.main.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.zumuniyo.main.dto.NoticeBoardDTO;
import com.zumuniyo.main.dto.QNoticeBoardDTO;

public interface NoticeBoardRepository extends QuerydslPredicateExecutor<NoticeBoardDTO>,PagingAndSortingRepository<NoticeBoardDTO, Long>{

	  @Modifying    
	  @Query
	  (value ="update noticeboard p set p.hitCount = p.hitCount + 1 where p.id = :id" ,  nativeQuery = true)    
	  int updateHitCount(Long id);

	  public default Predicate makePredicate(String type, String keyword) {
			 BooleanBuilder builder = new BooleanBuilder();
			 QNoticeBoardDTO board = QNoticeBoardDTO.noticeBoardDTO;
			 builder.and(board.noticeBoardSeq.gt(0));
			 //검색조건처리
			 if(type==null) return builder;
			 switch (type) {
			 case "title": builder.and(board.title.like("%" + keyword + "%")); break;
			 case "content": builder.and(board.content.like("%" + keyword + "%")); break;
			 case "writer": builder.and(board.writer.like("%" + keyword + "%")); break;
			 default: break;
			 }
			 return builder;
			 }
	  
	  


}
