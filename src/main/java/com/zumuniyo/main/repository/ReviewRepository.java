package com.zumuniyo.main.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.ReviewDTO;

public interface ReviewRepository extends QuerydslPredicateExecutor<ReviewDTO>,PagingAndSortingRepository<ReviewDTO, Long>{
	
//	List<ReviewDTO> findAllByMemSeq(Long seq);
	

	//회원의 리뷰
	@Query(value ="select * from review where member_mem_seq = ?1 order by review_regdate desc", nativeQuery = true)
	List<ReviewDTO> selectAllByMem(Long bno);
	
	
	//샵의 리뷰
	@Query(value="select review.* from review \r\n"
			+ "join ordergroup on(review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "where shop_shop_seq = ?1 \r\n"
			+ "order by review_seq desc", nativeQuery = true)
	List<ReviewDTO> selectAllByShop(Long bno);
	
//	// 	매장 주인의 샵 리뷰	
//	@Query(value ="SELECT r.*\r\n"
//			+ "FROM SHOP \r\n"
//			+ "JOIN ORDERGROUP o ON ( shop.SHOP_SEQ = o.SHOP_SHOP_SEQ)\r\n"
//			+ "JOIN \"MEMBER\" m  ON ( o.MEMBER_MEM_SEQ = m.MEM_SEQ)\r\n"
//			+ "JOIN REVIEW r ON (o.ORDER_GROUP_SEQ = r.ORDER_GROUP_ORDER_GROUP_SEQ)\r\n"
//			+ "WHERE shop.MEMBER_MEM_SEQ = ?1; ", nativeQuery = true)
//	List<ReviewDTO> selectAllByShopMem(Long bno);
//	
	//메뉴의 리뷰
	@Query(value="select review.* from review \r\n"
			+ "join ordergroup on (review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "join menu on (menu.shop_shop_seq  = shop.shop_seq)\r\n"
			+ "where menu_seq = ?1\r\n"
			+ "order by review_seq desc", nativeQuery = true)
	List<ReviewDTO> selectAllBymenu(Long bno);
	
	
	//매장의 추천리뷰
	@Query(value="select review.* from review \r\n"
			+ "join ordergroup on(review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "where shop_shop_seq = ?1 and review.review_exposure =1\r\n"
			+ "order by review_seq desc;", nativeQuery = true)
	List<ReviewDTO> selectByShopExposure(Long bno);
	
	
	
	
	//샵의 리뷰 평균별점
	@Query(value="select round(sum(review.review_taste+review.review_amount+review.review_service)/(count(*)*3),1 ) point from review \r\n"
			+ "join ordergroup on(review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "where shop_shop_seq = ?1", nativeQuery = true)
	int selectByShopRaty(Long bno);
	
	//메뉴의 리뷰 평균별점
	@Query(value="select round(sum(review.review_taste+review.review_amount+review.review_service)/(count(*)*3),1 ) point from review \r\n"
			+ "join ordergroup on (review.order_group_order_group_seq = ordergroup.order_group_seq) \r\n"
			+ "join shop on (shop.shop_seq = ordergroup.shop_shop_seq ) \r\n"
			+ "join menu on (menu.shop_shop_seq  = shop.shop_seq)\r\n"
			+ "where menu_seq = ?1\r\n", nativeQuery = true)
	int selectByMenuRaty(Long bno);
	
	@Query(value="select to_char(b.dt, 'yyyy-mm-dd') as review_regdate, nvl(sum(a.cnt), 0) cnt\r\n"
			+ "from ( select to_char(review_regdate, 'yyyy-mm-dd') as review_regdate , count(*) cnt\r\n"
			+ "from review  where review_regdate between to_date('2022-07-08', 'yyyy-mm-dd')  and to_date('2022-08-14', 'yyyy-mm-dd') \r\n"
			+ "group by review_regdate ) a , ( select to_date('2022-07-08', 'yyyy-mm-dd') + level - 1 as dt from dual \r\n"
			+ "connect by level <= (to_date('2022-08-14', 'yyyy-mm-dd')  - to_date('2022-07-08', 'yyyy-mm-dd') + 1) ) b  \r\n"
			+ "where b.dt = a.review_regdate(+) group by b.dt order by b.dt", nativeQuery = true)
	List<Map<String, Integer>> selectReviewCountDay();
	
	
	
	
	
}
