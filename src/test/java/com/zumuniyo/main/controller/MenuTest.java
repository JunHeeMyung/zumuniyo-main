package com.zumuniyo.main.controller;

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
public class MenuTest {
	
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuCategoryRepository menuCategoryRepository;
	
	
	
	
	
	
	
	
	
	
	//@Test
	public void MenuCategoryInsert() {
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder()
				.menuCategoryName("음료")
				.menuCategoryOrder(1)
				.shop(shop)
				.build();
		menuCategoryRepository.save(menuCategory);
	}
	
	
	@Test
	public void menuDelete() {
		
		menuRepository.findById(14L).ifPresentOrElse(menu->{
			
			menu.setMenuStatus(MenuStatus.비활성);
			
			menuRepository.save(menu);
			
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");
			
		});
		
	}
	
	
	//@Test
	public void menuRealDelete() {
		
		menuRepository.findById(13L).ifPresentOrElse(menu->{
			
			menuRepository.deleteById(13L);
			
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");
		
		});
		
	}
	
	
	//@Test
	public void menuUpdateOfCategory() {
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(11L).build();
		
		menuRepository.findById(12L).ifPresentOrElse(menu->{
			
			menu.setMenuCategory(menuCategory);
			
			menuRepository.save(menu);
			
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");
			
		});
	}
	
	
	//@Test
	public void menuInsert() {
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(5L).build();
		
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shop)
				.menuCategory(menuCategory)
				.menuName("삭제될메뉴")
				.menuPrice(10000)
				.menuImage("menu_img_del.jpg")
				.menuTop(false)
				.menuSimpleInfo("삭제될메뉴소개")
				.menuDetailInfo("삭제될메뉴설명")
				.menuStatus(MenuStatus.활성)
				.build();
		menuRepository.save(menu);
		
	}

}
