package com.zumuniyo.main.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.CouponDTO;
import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.OrderGroupDTO;
import com.zumuniyo.main.dto.ShopDTO;

public interface CouponRepository extends QuerydslPredicateExecutor<CouponDTO>,PagingAndSortingRepository<CouponDTO, Long>{

	List<CouponDTO> findByOrderGroup(OrderGroupDTO orderGroup);
	
	@Query(value = "SELECT COUPON_NAME AS 쿠폰이름,"
						+ "COUPONDC AS 할인액,"
						+ "COUPON_MIN_COND as 사용가능금액,"
						+ "COUPON_EXPIRE AS 유효기간, "
						+ "count(*) AS 발행수량, "
						+ "count(CASE WHEN MEMBER_MEM_SEQ IS NULL THEN 1 END) AS 잔여수량, "
						+ "count(CASE WHEN MEMBER_MEM_SEQ IS NOT NULL THEN 1 END) AS 배포수량, "
						+ "count(CASE WHEN ORDER_GROUP_ORDER_GROUP_SEQ IS NOT NULL THEN 1 END) AS 사용수량 "
						+ "FROM COUPON "
						+ "WHERE SHOP_SHOP_SEQ = :shopSeq "
						+ "GROUP BY (COUPON_NAME,COUPONDC,COUPON_MIN_COND,COUPON_EXPIRE) "
						+ "ORDER BY COUPON_EXPIRE DESC",nativeQuery = true)
	List<Map<String,Object>> getCouponData(Long shopSeq);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM COUPON WHERE "
			+ "MEMBER_MEM_SEQ IS NULL AND "
			+ "COUPON_NAME = :couponName AND "
			+ "COUPONDC = :couponDC AND "
			+ "COUPON_MIN_COND = :couponMinCond AND "
			+ "COUPON_EXPIRE = :couponExpire "
			+ "AND SHOP_SHOP_SEQ = :shopSeq",nativeQuery = true)
	void deleteUnusedCoupon(String couponName,int couponDC,int couponMinCond,Timestamp couponExpire,long shopSeq);
	
	List<CouponDTO> findByMemberOrderByCouponExpireDesc(MemberDTO member);
	List<CouponDTO> findByShop(ShopDTO shop);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE COUPON "
			+ "SET MEMBER_MEM_SEQ = :memberSeq  "
			+ "WHERE COUPON_SEQ =( "
			+ "SELECT min(COUPON_SEQ) "
			+ "FROM COUPON "
			+ "WHERE COUPONDC = :couponDC "
			+ "AND COUPON_EXPIRE = :couponExpire "
			+ "AND COUPON_MIN_COND = :couponMinCond "
			+ "AND SHOP_SHOP_SEQ = :shopSeq "
			+ "AND COUPON_NAME = :couponName "
			+ "AND MEMBER_MEM_SEQ IS NULL)",nativeQuery = true)
	void getCoupon(Long memberSeq,int couponDC,int couponMinCond,Timestamp couponExpire,long shopSeq,String couponName);
	
	List<CouponDTO> findByCouponDCAndCouponMinCondAndCouponExpireAndCouponNameAndShopAndMember(int couponDC,int couponMinCond,Timestamp couponExpire,String couponName,ShopDTO shop,MemberDTO member);
	
	
}