package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.zumuniyo.main.dto.ReviewDTO;

public interface ReviewRepository extends QuerydslPredicateExecutor<ReviewDTO>,PagingAndSortingRepository<ReviewDTO, Long>{
	
//	List<ReviewDTO> findAllByMemSeq(Long seq);
	

	@Query(value ="select * from review where member_mem_seq = ? order by review_regdate desc", nativeQuery = true)
	List<ReviewDTO> selectAllByMem(Long bno);
	
//	@Query(value ="", nativeQuery = true)
//	List<ReviewDTO> selectAllByShop(Long bno);
	
	@Query(value="select review.* from review \r\n"
			+ "join ordergroup on(review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "where shop_shop_seq = ?\r\n"
			+ "order by review_seq desc;", nativeQuery = true)
	List<ReviewDTO> selectAllByShop(Long bno);
	
	@Query(value="select review.* from review \r\n"
			+ "join ordergroup on (review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "join menu on (menu.shop_shop_seq  = shop.shop_seq)\r\n"
			+ "where menu_seq = ?\r\n"
			+ "order by review_seq desc;", nativeQuery = true)
	List<ReviewDTO> selectAllBymenu(Long bno);
	
	
}
