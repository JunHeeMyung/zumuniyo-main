package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.ShopDTO;

public interface MenuCategoryRepository extends QuerydslPredicateExecutor<MenuCategoryDTO>,PagingAndSortingRepository<MenuCategoryDTO, Long>{
	
	
	Long countByShop(ShopDTO shop);
	
	List<MenuCategoryDTO> findByShopAndMenuCategoryName(ShopDTO shop, String menuCategoryName);
	
	List<MenuCategoryDTO> findByShopAndMenuCategoryOrderNotOrderByMenuCategoryOrderAsc(ShopDTO shop, int order);
	
	List<MenuCategoryDTO> findByShop(ShopDTO shop);
	
	List<MenuCategoryDTO> findByShopAndMenuCategoryOrder(ShopDTO shop, int order);
	
	List<MenuCategoryDTO> findByShopAndMenuCategoryOrderGreaterThan(ShopDTO shop, int order);
}
