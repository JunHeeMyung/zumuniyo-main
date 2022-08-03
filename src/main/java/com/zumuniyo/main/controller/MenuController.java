package com.zumuniyo.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.MenuRepository;
import com.zumuniyo.main.repository.ShopRepository;

import lombok.extern.java.Log;


@Log
@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	String insertResult;
	
	
	
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:menu";
	}
	
	
	
	// 메뉴리스트 조회
	@GetMapping("/menulist/{shopseq}")
	public List<MenuDTO> menuList(@PathVariable Long shopseq) {
		
		List<MenuDTO> menuList = new ArrayList<>();
		
		shopRepository.findById(shopseq).ifPresent(shop->{
			
			menuRepository.findByShopAndMenuStatusNot(shop, MenuStatus.비활성).forEach(s->{
				
				menuList.add(s);
			});
			
		});
		
		return menuList;
	}
	
	
	
	// 메뉴등록페이지
	
	/*
	@GetMapping("/menuInsert")
	public String menuInsertGet() {
		
		return "menu/menuInsert";
	}
	*/
	
	
	@PostMapping("/menuInsert/{shopseq}")
	public String menuInsertPost(@PathVariable Long shopseq , Long cno, MenuDTO menuDTO ) {
		
		ShopDTO shop = ShopDTO.builder().shopSeq(shopseq).build();
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(cno).build();
		
		
		insertResult="";
		
		if(menuDTO.getMenuName()==null||menuDTO.getMenuName()=="") return "메뉴 이름을 입력해주세요.";
			
		if(!Pattern.matches("^[가-힣]{2,16}$", menuDTO.getMenuName())) return "메뉴 이름은 한글 2~16자로 작성해주세요.";	
		
		
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shop)
				.menuCategory(menuCategory)
				.menuName(menuDTO.getMenuName())
				.menuPrice(menuDTO.getMenuPrice())
				.menuImage(menuDTO.getMenuImage())
				.menuTop(menuDTO.isMenuTop())
				.menuSimpleInfo(menuDTO.getMenuSimpleInfo())
				.menuDetailInfo(menuDTO.getMenuDetailInfo())
				.menuStatus(MenuStatus.활성)
				.build();
		
		MenuDTO result = menuRepository.save(menu);
		
		log.info("[메뉴등록]:" + result.toString());
		
		return "메뉴 등록이 완료되었습니다.";
	}
	
	
	
	
	
	
	
}
