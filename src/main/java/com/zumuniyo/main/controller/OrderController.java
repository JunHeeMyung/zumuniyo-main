package com.zumuniyo.main.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.OrderDTO;
import com.zumuniyo.main.dto.OrderGroupDTO;
import com.zumuniyo.main.dto.OrderStatus;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.CouponRepository;
import com.zumuniyo.main.repository.MenuRepository;
import com.zumuniyo.main.repository.OrderGroupRepository;
import com.zumuniyo.main.repository.OrderRepository;
import com.zumuniyo.main.repository.ShopRepository;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderGroupRepository orderGroupRepository;
	@Autowired
	CouponRepository couponRepository;
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	ShopRepository shopRepository;
	
	String orderResult;
	ShopDTO validShop;
	List<OrderDTO> validOrderList; 
	List<OrderDTO> orderList;
	OrderGroupDTO orderGroup;
	
	
	
	@GetMapping("/orderlist/{orderSeq}")
	public List<OrderDTO> getOrderList (@PathVariable Long orderSeq
			,HttpServletRequest request){
		
		orderList=null;
		if(orderSeq==null) return null;
		
		orderGroupRepository.findById(orderSeq).ifPresent(orderGroup->{
			orderList = orderRepository.findByOrderGroup(orderGroup);
		});

		return orderList;
	}
	
	@PostMapping("/orderlist")
	public String order(	@RequestParam(defaultValue = "0") Integer tableNum ,
							@RequestParam(defaultValue = "0") Long shopSeq,
							@RequestParam(defaultValue = "",name = "orderList") String inputOrderList,
							@RequestParam(defaultValue = "0") Long couponSeq ,
							HttpServletRequest request) {
		
		log.info("[주문요청]:tableNum :"+tableNum+"/"
				+ "shopSeq :"+shopSeq+"/"
				+ "orderList: "+inputOrderList+"/"
				+ "couponSeq: "+couponSeq);
		
		/* 입력검증 */
		LinkedHashMap<Long,Integer> orderList = new LinkedHashMap<Long,Integer>();
		try {
		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject =  (JSONObject)jsonParser.parse(inputOrderList);
			for(Object key :jsonObject.keySet()) {
				orderList.put(Long.parseLong(key.toString()), Integer.parseInt(jsonObject.get(key).toString()));
			}
			
			System.out.println(orderList);
			
		}catch (Exception e) {
			return "입력 데이터의 형식이 이상합니다";
		}
		
		if(tableNum==0) return "테이블 번호가 없습니다";
		if(shopSeq==0) return "매장 번호가 없습니다";
		if(orderList.size()==0) return "주문이 없습니다";
		if(request.getSession().getAttribute("member")==null) return "로그인 정보가 없습니다";
		
		orderResult = "";
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		Timestamp now = new Timestamp(System.currentTimeMillis());
	
		if(couponSeq!=0) {
			/* 쿠폰 유효성검증 */
			couponRepository.findById(couponSeq).ifPresentOrElse(
					coupon->{
						if(!coupon.getMember().equals(loginedMember)) {
							orderResult = "쿠폰 주인이 아닙니다";
							return;
						};
						if(coupon.getCouponExpire().after(now)) {
							orderResult = "사용기간이 지난 쿠폰입니다";
							return;
						};
						if(coupon.getOrderGroup()!=null) {
							orderResult = "이미 사용된 쿠폰입니다";
							return;
						};
					}
					, ()->{
						orderResult = "유효하지 않는 쿠폰입니다";
					});
			
			if(!orderResult.equals("")) {
				log.info("[주문결과]:"+orderResult);
				return orderResult;
			}
		}
		
		/* 매장 유효성검증 */
		validShop = null;
		shopRepository.findById(shopSeq).ifPresentOrElse(shop->{
			validShop=shop;
		}, ()->{
			orderResult = "유효하지 않는 매장입니다";
		});
		
		if(!orderResult.equals("")) {
			log.info("[주문결과]:"+orderResult);
			return orderResult;
		}
		
		/* 메뉴 유효성 검증*/
		validOrderList = new ArrayList<OrderDTO>();
		for(Long key :orderList.keySet()) {
			menuRepository.findById(key).ifPresentOrElse(
					menu->{
						OrderDTO validOrder=OrderDTO.builder()
								.count(orderList.get(key))
								.menu(menu)
								.build();
						validOrderList.add(validOrder);
					}
					, ()->{
						orderResult = "유효하지 않는 메뉴입니다";
						return;
					});
		}
		if(orderList.size()!=validOrderList.size()) {
			log.info("[주문결과]:"+orderResult);
			return orderResult;
		}
		
		/* 오더그룹생성 */
		orderGroup = OrderGroupDTO.builder()
				.tableNum(tableNum)
				.shop(validShop)
				.member(loginedMember)
				.orderStatus(OrderStatus.주문대기)
				.build();
		orderGroup = orderGroupRepository.save(orderGroup);
		
		/* 오더생성 */
		validOrderList.forEach(validOrder->{
			validOrder.setOrderGroup(orderGroup);
			orderRepository.save(validOrder);
		});
		
		/* 쿠폰사용 */
		if(couponSeq!=0) {
			couponRepository.findById(couponSeq).ifPresent(coupon->{
				coupon.setOrderGroup(orderGroup);
				couponRepository.save(coupon);
			});
		}
	
		return "주문성공:"+orderGroup.getOrderGroupSeq();
	}
	

}
