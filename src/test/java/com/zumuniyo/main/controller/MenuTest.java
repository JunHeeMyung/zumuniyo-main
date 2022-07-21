package com.zumuniyo.main.controller;

import java.awt.Menu;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.MenuCategoryRepository;
import com.zumuniyo.main.repository.MenuRepository;
import com.zumuniyo.main.repository.ShopRepository;

@SpringBootTest
public class MenuTest {
	
	
	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	MenuCategoryRepository menuCategoryRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	
	@Test
	public void MenuCategoryDelete() {
		
		Long cno = 26L;
		
		//Long categoryDefault = menuCategoryRepository.findByMenuCategoryName("카테고리없음");
		
		menuCategoryRepository.findById(cno).ifPresent(mc->{
			//menuRepository.findByMenuCategory(mc).forEach(System.out::println);
			
			List<MenuDTO> menulist = menuRepository.findByMenuCategory(mc);
			System.out.println(menulist);
			
			//mc.setMenuCategorySeq(categoryDefault);
			
			
			//menuRepository.save();
			
		});
		
		
	
		
	}
	
	
	//@Test
	public void MenuCategoryInsert() {
		

		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		Long categoryCount = menuCategoryRepository.countByShop(shop);

		System.out.println(categoryCount);

		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder()
				.menuCategoryName("카테고리없음")
				.menuCategoryOrder(Integer.parseInt(categoryCount.toString()) + 1)
				.shop(shop).build();
		menuCategoryRepository.save(menuCategory);

	}
	
	
	//@Test
	public void menuSelectAll() {

	
		System.out.println("==========================start============================");
		
		menuRepository.findByMenuStatusNot(MenuStatus.비활성).forEach(System.out::println);
		
		System.out.println("===========================end=============================");
	}
	
	
	
	
	//@Test
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
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(26L).build();
		
		IntStream.rangeClosed(1, 5).forEach(i->{
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shop)
				.menuCategory(menuCategory)
				.menuName("카테고리삭제된메뉴" + i)
				.menuPrice(10000)
				.menuImage("menu_img_del_"+ i + ".jpg")
				.menuTop(false)
				.menuSimpleInfo("메뉴소개" + i)
				.menuDetailInfo("메뉴설명" + i)
				.menuStatus(MenuStatus.활성)
				.build();
		menuRepository.save(menu);
			
		});
	}
	


}
