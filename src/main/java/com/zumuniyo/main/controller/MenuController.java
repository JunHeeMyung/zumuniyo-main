package com.zumuniyo.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;
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
	
	
	
	
	
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:menu";
	}
	
	
	
	
	@GetMapping("/menulist/{shopseq}")
	public List<MenuDTO> menuList(@PathVariable Long shopseq) {
		
		System.out.println(shopseq+":요청들어옴");
		
		List<MenuDTO> menuList = new ArrayList<>();
		
		shopRepository.findById(shopseq).ifPresent(shop->{
			
			menuRepository.findByShopAndMenuStatusNot(shop, MenuStatus.비활성).forEach(s->{
				menuList.add(s);
			});
			
		});
		
		return menuList;
	}
	
	
	
	
	
	
	
	
	
}
