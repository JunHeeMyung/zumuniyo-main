package com.zumuniyo.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.MenuCategoryRepository;
import com.zumuniyo.main.repository.MenuRepository;

@SpringBootTest
public class MenuCrudTest {
	
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuCategoryRepository menuCategoryRepository;
	
	
	//@Test
	public void MenuCategoryInsert() {
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder()
				.menuCategoryName("주메뉴")
				.menuCategoryOrder(1)
				.shop(shop)
				.build();
		menuCategoryRepository.save(menuCategory);
	}
	
	
	@Test
	public void menuInsert() {
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(5L).build();
		
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shop)
				.menuCategory(menuCategory)
				.menuName("스사노오덮밥")
				.menuPrice(7500)
				.menuImage("menu_img_002.jpg")
				.menuTop(false)
				.menuSimpleInfo("한공기로 든든해요")
				.menuDetailInfo("원산지: 나뭇잎마을")
				.menuStatus(MenuStatus.활성)
				.build();
		menuRepository.save(menu);
		
	}

}
