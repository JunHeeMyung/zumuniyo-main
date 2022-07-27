package com.zumuniyo.main.controller;


import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	
	
	
	//@Test
	public void shopFindByMenu() {
		// 특정 이름이 포함된 메뉴를 가진 매장들 전부조회
		// 나중에 자바스크립트에서 forEach 돌린거 중복제거해야됨
		
		String searchName = "re";
		
		menuRepository.findByMenuNameContaining(searchName).forEach(menu->{
			
			Long findShop = menu.getShop().getShopSeq();
			
			System.out.println(findShop);
		});
		
	}
	
	//@Test
	public void menuFindByName() {
		// 특정매장의 특정 메뉴이름 포함된 메뉴들 전부조회
		
		String searchName = "re";
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		menuRepository.findByShopAndMenuNameContaining(shop, searchName).forEach(System.out::println);	
			
	}
	
	
	//@Test
	public void menuCategoryOrderChange() {
		// 카테고리 순서값 두개를 서로 변경
		
		Long cno1 = 10L;
		
		Long cno2 = 11L;
		
		menuCategoryRepository.findById(cno1).ifPresent(origin->{
			
			menuCategoryRepository.findById(cno2).ifPresent(target->{
				
				int orderOrigin = origin.getMenuCategoryOrder();
				int orderTarget = target.getMenuCategoryOrder();
				
				int temp;
				temp = orderOrigin;
				orderOrigin = orderTarget;
				orderTarget = temp;
				
				origin.setMenuCategoryOrder(orderOrigin);
				target.setMenuCategoryOrder(orderTarget);
				
				menuCategoryRepository.save(target);
			});
			menuCategoryRepository.save(origin);
		});
		
		
	}
	
	
	
	
	//@Test
	public void menuCategoryDelete() {
		// 특정 카테고리를 지우기 전에 기존에 거기에 해당되는 메뉴들을 우선 '카테고리없음'카테고리로 전부 옮긴 후, 그다음에 그 특정 카테고리를 지운다.
		
		Long cno = 63L;
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		menuCategoryRepository.findByShopAndMenuCategoryName(shop, "카테고리없음").forEach(i -> {

			Long categoryDefault = i.getMenuCategorySeq();

			System.out.println(categoryDefault);

			MenuCategoryDTO menuCategoryUpdate = MenuCategoryDTO.builder().menuCategorySeq(categoryDefault).build();

			menuCategoryRepository.findById(cno).ifPresent(mc -> {
				menuRepository.findByMenuCategoryOrderByMenuSeq(mc).forEach(menu -> {

					menu.setMenuCategory(menuCategoryUpdate);

					menuRepository.save(menu);
				});
				
				menuCategoryRepository.findById(cno).ifPresent(category -> {
					
					menuCategoryRepository.deleteById(cno);
				});
				
			});

		});

	}
	
	
	
	//@Test
	public void menuCategoryInsert() {
		// 프론트단에서 insert모달 켰을때 현재 디비에 존재하는 카테고리들 읽어옴...
		// 만약 디비에 있는 카테고리 개수가 0개이면 백엔드에 '카테고리없음' 카테고리를 하나 만들라고 지정함(프론트에서 할거)
		// 카테고리없음 카테고리 만들때는 menuCategoryOrder 값을 0으로 줌 
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4883L).build();
		
		Long categoryCount = menuCategoryRepository.countByShop(shop);

		System.out.println(categoryCount);

		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder()
				.menuCategoryName("음료")
				.menuCategoryOrder(Integer.parseInt(categoryCount.toString()))
				.shop(shop).build();
		menuCategoryRepository.save(menuCategory);

	}
	
	
	//@Test
	public void menuSelectByCategory() {
		// 특정 카테고리에 있는 메뉴 전부조회
		
		Long cno = 5L;
			
		MenuCategoryDTO mc = MenuCategoryDTO.builder().menuCategorySeq(cno).build();
		
		menuRepository.findByMenuCategoryOrderByMenuSeq(mc).forEach(System.out::println);
		
	}
		
	
	
	//@Test
	public void menuCategorySelectAll() {
		// 특정 매장의 모든 카테고리 조회(0번오더 빼고)
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		menuCategoryRepository.findByShopAndMenuCategoryOrderNotOrderByMenuCategoryOrderAsc(shop, 0).forEach(System.out::println);
		
	}
	
	
	//@Test
	public void menuSelectAll() {
		// 메뉴를 전부 보여주는게 아니라 비활성이 아닌 메뉴만 보여줌 + 매장번호 4882인 메뉴만
		
		ShopDTO shop = ShopDTO.builder().shopSeq(4882L).build();
		
		System.out.println("==========================start============================");
		
		//menuRepository.findByMenuStatusNot(MenuStatus.비활성).forEach(System.out::println);
		
		menuRepository.findByShopAndMenuStatusNot(shop, MenuStatus.비활성).forEach(System.out::println);
		
		
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
		//이건안씀... 아예 디비에서 날릴때
		menuRepository.findById(13L).ifPresentOrElse(menu->{
			
			menuRepository.deleteById(13L);
			
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");
		
		});
		
	}
	
	//@Test
	public void menuUpdateOfTop() {
		// 특정 메뉴를 추천메뉴로 변경여부
		Long menuseq = 6L;
		
		menuRepository.findById(menuseq).ifPresentOrElse(menu->{
			
			menu.setMenuTop(true);
			//menu.setMenuTop(false);
			
			menuRepository.save(menu);
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");});
		
	}
	
	
	//@Test
	public void menuUpdateOfInfo() {
		// 메뉴 설명만 수정
		Long menuseq = 12L;
		
		menuRepository.findById(menuseq).ifPresentOrElse(menu->{
			
			menu.setMenuSimpleInfo("쓰지 않고 달달해요");
			menu.setMenuDetailInfo("원산지: 소리마을");
			
			menuRepository.save(menu);
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");});
		
	}
	
	
	
	//@Test
	public void menuUpdateOfCategory() {
		// 특정 메뉴 카테고리만 변경
		
		Long menuseq = 12L;
		
		Long cateseq = 11L;
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(cateseq).build();
		
		menuRepository.findById(menuseq).ifPresentOrElse(menu->{
			
			menu.setMenuCategory(menuCategory);
			
			menuRepository.save(menu);
			
		}, ()->{System.out.println("해당 메뉴아이디가 존재하지 않습니다.");
			
		});
	}
	
	
	//@Test
	public void menuInsert() {

		ShopDTO shop = ShopDTO.builder().shopSeq(4883L).build();
		
		Long cno = 67L;
		
		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder().menuCategorySeq(cno).build();
		
		IntStream.rangeClosed(1, 3).forEach(i->{
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shop)
				.menuCategory(menuCategory)
				.menuName("테스트음료" + i)
				.menuPrice(2000)
				.menuImage("menu_img_bever_test_"+ i + ".jpg")
				.menuTop(false)
				.menuSimpleInfo("메뉴소개" + i)
				.menuDetailInfo("메뉴설명" + i)
				.menuStatus(MenuStatus.활성)
				.build();
		menuRepository.save(menu);
			
		});
	}
	


}
