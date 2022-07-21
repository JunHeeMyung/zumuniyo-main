package com.zumuniyo.main.repository;

import java.util.Optional;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.ShopDTO;

public interface MenuCategoryRepository extends QuerydslPredicateExecutor<MenuCategoryDTO>,PagingAndSortingRepository<MenuCategoryDTO, Long>{
	
	
	Long countByShop(ShopDTO shop);
	
	Long findByMenuCategoryName(String menuCategoryName);
	
}
