package com.zumuniyo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.CouponDTO;
import com.zumuniyo.main.repository.CouponRepository;
import com.zumuniyo.main.repository.OrderGroupRepository;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	CouponRepository couponRepository;
	@Autowired
	OrderGroupRepository orderGroupRepository;
	
	List<CouponDTO> couponListDTO;
	CouponDTO couponDTO;
	
	/* 쿠폰사용여부 확인용 */ 
	@GetMapping("/usedcoupon/{orderGroupSeq}")
	public CouponDTO getUsedCoupon(@PathVariable Long orderGroupSeq){
		
		couponDTO=null;
		couponListDTO=null;
		
		orderGroupRepository.findById(orderGroupSeq).ifPresent(orderGroup->{
			couponListDTO = couponRepository.findByOrderGroup(orderGroup);
			if(couponListDTO.size()==1) couponDTO = couponListDTO.get(0);
		});
		
		return couponDTO;
	}
	
}
