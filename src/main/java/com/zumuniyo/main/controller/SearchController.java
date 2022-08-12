package com.zumuniyo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.ShopCategory;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.SearchRepository;

import lombok.extern.java.Log;


@Log
@RestController
public class SearchController {

	@Autowired
	SearchRepository searchRepo;
	///추가
	@RequestMapping("/shopsearch")
	public List<ShopDTO> searchShopResult( String keyword){
//		ShopCategory shopCategory = null;
//		ShopCategory[] arr = ShopCategory.values();
//		for(ShopCategory category : arr) {
//			if(category.name().equals(keyword)) {
//				shopCategory  = ShopCategory.valueOf(keyword);
//			}
//		}
		
		 
		
		List<ShopDTO> shopList =searchRepo.findByShopNameContainingOrShopInfoContainingOrShopCategory(keyword, keyword,keyword) ;
		System.out.println("샵서치:"+ shopList);
		
		return shopList;
		
	}
	
	
	
}
