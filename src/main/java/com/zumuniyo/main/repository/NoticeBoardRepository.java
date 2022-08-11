package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.zumuniyo.main.dto.NoticeBoardDTO;
import com.zumuniyo.main.dto.QNoticeBoardDTO;

public interface NoticeBoardRepository extends QuerydslPredicateExecutor<NoticeBoardDTO>,PagingAndSortingRepository<NoticeBoardDTO, Long>{

	  @Modifying    
	  @Query
	  (value ="update noticeboard p set p.hit_Count = p.hit_Count + 1 where p.notice_Board_Seq = ?1" ,  nativeQuery = true)    
	  int updateHitCount(@Param("noticeBoardSeq")Long NoticeBoardDTO);

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
	  
//		SELECT *
//		FROM  NoticeBoard
//		ORDER BY DECODE(boardTop, '1', 1), noticeBoardSeq ASC;
//	  @Query(value = "select * from NoticeBoard order by decode(boardTop,'1',1) noticeBoardSeq ASC",nativeQuery =true)
//	  List<NoticeBoardDTO> findAllByOrderByBorderTopAsc();


}
