package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;

public interface MenuRepository extends QuerydslPredicateExecutor<MenuDTO>,PagingAndSortingRepository<MenuDTO, Long>{
	
	
	List<MenuDTO> findByMenuStatusNot(MenuStatus menuStatus);
	
	Page<MenuDTO> findByMenuStatusNot(MenuStatus menuStatus,Pageable p);
	
	Long countByMenuStatusNot(MenuStatus menuStatus);
	
	List<MenuDTO> findByMenuCategory(MenuCategoryDTO menuCategory);

}
