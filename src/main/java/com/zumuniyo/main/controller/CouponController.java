package com.zumuniyo.main.controller;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.CouponDTO;
import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.OrderDTO;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.CouponRepository;
import com.zumuniyo.main.repository.OrderGroupRepository;
import com.zumuniyo.main.repository.ShopRepository;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	CouponRepository couponRepository;
	@Autowired
	OrderGroupRepository orderGroupRepository;
	@Autowired
	ShopRepository shopRepository;
	
	List<CouponDTO> couponListDTO;
	CouponDTO couponDTO;
	ShopDTO shopDTO;
	
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
	
	/* 쿠폰발급 */
	@PostMapping("/list")
	public String createCoupon(@RequestParam(defaultValue = "") String couponName,
							   @RequestParam(defaultValue = "0") Long shopSeq,
							   @RequestParam(defaultValue = "-1") Integer couponMinCond,
							   @RequestParam(defaultValue = "-1") Integer couponDC,
							   @RequestParam(defaultValue = "") String couponExpire,
							   @RequestParam(defaultValue = "0") Integer couponCount,
							   HttpServletRequest request) {
		
		/* 입력값 검증 */
		if(couponExpire.equals("")) return "유효기간이 유효하지 않습니다";
		ZonedDateTime zonedCouponExpire = ZonedDateTime.parse(couponExpire+"+09:00");
		Timestamp timeStampCouponExpire = Timestamp.valueOf(zonedCouponExpire.toLocalDateTime());
		
		if(couponName.equals("")) return "쿠폰 이름이 없습니다";
		if(shopSeq==0L) return "매장 번호가 없습니다";
		if(couponMinCond==-1) return "사용가능금액이 없습니다";
		if(couponDC==-1) return "할인액이 없습니다";
		if(couponMinCond<1000) return "유효하지 않은 사용가능금액입니다 (1,000원 이상 가능)";
		if(couponDC<1000) return "유효하지 않은 할인 금액입니다 (1,000원 이상 가능)";
		if(couponMinCond<couponDC) return "사용가능금액은 할인금액보다 커야합니다";
		if(couponCount<=0) return "유효하지 않은 쿠폰 수량입니다 (1~100장)";
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return "로그인 정보가 없습니다";
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		if(shopDTO==null) return "매장번호가 유효하지않습니다";
		if(!shopDTO.getMember().equals(loginedMember.getMemSeq())) return "매장 주인이 아닙니다";
		Timestamp now = new Timestamp((new Date().getTime()));
		if(timeStampCouponExpire.before(now)) return "유효기간은 미래만 가능합니다";

		/* 쿠폰발급 */
		for(int i=0;i<couponCount;i++) {
			CouponDTO coupon = CouponDTO.builder().couponName(couponName)
					   .shop(shopDTO)
					   .couponMinCond(couponMinCond)
					   .couponDC(couponDC)
					   .couponExpire(timeStampCouponExpire)
					   .build();
			CouponDTO result = couponRepository.save(coupon);
			log.info("[쿠폰발급]:"+result);
		}

		return "쿠폰발급에 성공했습니다";
	}
	
	/* 해당매장의 쿠폰데이터 -관리조회용*/
	@GetMapping("/list")
	public List<Map<String,Object>> getCouponData(@RequestParam(defaultValue = "0") Long shopSeq,HttpServletRequest request) {
		
		/* 조건 검증 */
		if(shopSeq==0L) return null;
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return null;
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		if(shopDTO==null) return null;
		if(!shopDTO.getMember().equals(loginedMember.getMemSeq())) return null;

		return couponRepository.getCouponData(shopSeq);
	}
	
	/* 아직배포안된쿠폰만삭제 */
	@DeleteMapping("/unused")
	public String deleteUnusedCoupon(  @RequestParam(defaultValue = "") String couponName,
									   @RequestParam(defaultValue = "0") Long shopSeq,
									   @RequestParam(defaultValue = "-1") Integer couponMinCond,
									   @RequestParam(defaultValue = "-1") Integer couponDC,
									   @RequestParam(defaultValue = "") String couponExpire,
										HttpServletRequest request) {
		
		if(couponName.equals("")) return "쿠폰이름이 없습니다";
		if(shopSeq==0) return "매장이 없습니다";
		if(couponMinCond==-1) return "사용가능금액이 없습니다";
		if(couponDC==-1) return "할인액이 없습니다";
		if(couponExpire.equals("")) return "유효기간이 없습니다";

		ZonedDateTime zonedCouponExpire = ZonedDateTime.parse(couponExpire+"+09:00");
		Timestamp timeStampCouponExpire = Timestamp.valueOf(zonedCouponExpire.toLocalDateTime());
		
		if(shopSeq==0L) return "매장 번호가 없습니다";
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return "로그인 정보가 없습니다";
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		if(shopDTO==null) return "매장번호가 유효하지않습니다";
		if(!shopDTO.getMember().equals(loginedMember.getMemSeq())) return "매장 주인이 아닙니다";

		couponRepository.deleteUnusedCoupon(couponName, couponDC, couponMinCond, timeStampCouponExpire, shopSeq);
		
		return "미배포된 쿠폰을 제거하였습니다";
	}
	
	/* 내쿠폰리스트 */
	@GetMapping("/mycoupon")
	public List<CouponDTO> getMyCouponList(HttpServletRequest request) {
		
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return null;
		return couponRepository.findByMemberOrderByCouponExpireDesc(loginedMember);
		
	}

	/* 해당매장의쿠폰리스트 - 발급조회용*/
	@GetMapping("/list/{shopSeq}")
	public List<Map<String,Object>> getShopCouponList(@PathVariable Long shopSeq,
														HttpServletRequest request) {
		shopDTO=null;
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		return couponRepository.getCouponData(shopSeq);
		
	}
	
	/* 쿠폰배포받기 */
	@PutMapping("/mycoupon")
	public String getCoupon(HttpServletRequest request,
							@RequestParam(defaultValue = "-1") int couponDC,
							@RequestParam(defaultValue = "-1") int couponMinCond,
							@RequestParam(defaultValue = "") String couponExpire,
							@RequestParam(defaultValue = "") String couponName,
							@RequestParam(defaultValue = "0") Long shopSeq) {
		
		if(couponDC==-1) return "할인액이 없습니다";
		if(couponMinCond==-1) return "사용가능금액이 없습니다";
		if(couponExpire.equals("")) return "유효기간이 없습니다";
		if(couponDC==-1) return "할인액이 없습니다";
		if(couponName.equals("")) return "쿠폰이름이 없습니다";
		if(shopSeq==0) return "매장이 없습니다";

		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return "로그인 정보가 없습니다";
		
		ZonedDateTime zonedCouponExpire = ZonedDateTime.parse(couponExpire+"+09:00");
		Timestamp timeStampCouponExpire = Timestamp.valueOf(zonedCouponExpire.toLocalDateTime());
		
		shopDTO=null;
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		if(shopDTO==null) return "존재하지 않는 매장입니다";
		
		List<CouponDTO> coupon = couponRepository.findByCouponDCAndCouponMinCondAndCouponExpireAndCouponNameAndShopAndMember(couponDC, couponMinCond, timeStampCouponExpire, couponName, shopDTO, loginedMember);
		
		if(coupon.size()>0) return "이미 발급받은 쿠폰입니다";
		
		couponRepository.getCoupon(loginedMember.getMemSeq(),couponDC,couponMinCond,timeStampCouponExpire,shopSeq,couponName);
		
		return "쿠폰이 발급되었습니다";
		
	}
	
	/* 매장 쿠폰 선택용 */
	@GetMapping("/mycoupon/{shopSeq}")
	List<CouponDTO> getShopMyCouponList(@PathVariable Long shopSeq,
			HttpServletRequest request){
		
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return null;
		
		shopDTO=null;
		shopRepository.findById(shopSeq).ifPresent(shop->{shopDTO=shop;});
		if(shopDTO==null) return null;
		
		Timestamp now = new Timestamp((new Date().getTime()));
		return couponRepository.findByMemberAndShopAndOrderGroupIsNullAndCouponExpireGreaterThanOrderByCouponExpireDesc(loginedMember, shopDTO, now);
	}
	
	
}
