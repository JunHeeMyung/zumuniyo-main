package com.zumuniyo.main.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.LocationDTO;
import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.Memtype;
import com.zumuniyo.main.dto.ShopCategory;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.dto.ShopStatus;
import com.zumuniyo.main.repository.LocationRepository;
import com.zumuniyo.main.repository.ShopRepository;

@RestController
@RequestMapping("/shop/*")
public class ShopController {

	@Autowired
	ShopRepository shopRepo;
	
	@Autowired
	LocationRepository locationRepository;

	@GetMapping("/shopTest")
	public String shopTest() {
		return "도착";
	}
//	shopListByMember
	@GetMapping("/shopList")
	public List<ShopDTO> shopList() {
		List<ShopDTO> shopList = (List<ShopDTO>) shopRepo.findAll(Sort.by(Sort.Direction.DESC, "shopRegdate"));
		return shopList;
	}
	
	@GetMapping("/shopListByMem")
	public List<ShopDTO> shopListByMem(HttpServletRequest request) {
		List<ShopDTO> shoplist = new ArrayList<>();
		
		
		MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member"); 
		if (mem == null) return shoplist;
		if(mem.getMemType()!= Memtype.사업자회원) return shoplist;	
		
		
		List<ShopDTO> shopList = (List<ShopDTO>) shopRepo.shopListByMember(mem.getMemSeq());
		return shopList;
	}
	
	@GetMapping("/shopListByseq/{seq}")
	public ShopDTO shopInfo(@PathVariable Long seq) {
		System.out.println("값 들어옴");
//		List<ShopDTO> shopList = (List<ShopDTO>) shopRepo.findAll(Sort.by(Sort.Direction.DESC, "shopRegdate"));
		ShopDTO shop =  shopRepo.findById(seq).get();	
		
		
		return shop;
	}
	
//	@GetMapping("/shopListBymem")
//	public List<ShopDTO> shopListBymem(HttpServletRequest request) {
//		List<ShopDTO> shopList = new ArrayList<>();
//		if (request.getSession().getAttribute("member") != null) {
//			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
//			shopList = (List<ShopDTO>) shopRepo.findByMember(mem);
//		}
//		return shopList;
//	}

	@PostMapping("/shopInsert")
	public String shopInsert(ShopDTO shop, HttpServletRequest request, String locAddr, double locLat, double locLon) {
		
		System.out.println("샵입력 들어옴" + shop + "---" +  locAddr + locLat + locLon);
		LocationDTO locationDTO =  LocationDTO.builder()
				.locAddr(locAddr).locLat(locLat).locLon(locLon)
				.build();
		locationRepository.save(locationDTO);
		
		System.out.println("locationDTO :"+locationDTO);
		shop.setLocation(locationDTO);
		MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
		if (mem == null) return "로그인 정보가 없습니다";
		if(mem.getMemType()!= Memtype.사업자회원) return "사업자 회원이 아닙니다";
		
		shop.setMember(mem);			
		shop.setShopStatus(ShopStatus.활성);
		ShopDTO result = shopRepo.save(shop);
		
		System.out.println(shop);
		System.out.println(result);
		
		return 	(result==null||result.getShopSeq()==null)?"실패":"성공";
	}

}
