package com.zumuniyo.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MenuCategoryDTO;
import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.dto.MenuStatus;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.MenuCategoryRepository;
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
		
	@Autowired
	MenuCategoryRepository menuCategoryRepository;
	
	String insertResult;
	boolean started;
	MenuCategoryDTO tempMenuCategory; 
	ShopDTO tempShop; 
	List<MenuDTO> menuListByCategory;
	Optional<MenuDTO> menuDTOselected; 
	
	MenuCategoryDTO upperMenuCategory;
	MenuCategoryDTO lowerMenuCategory;
	
	List<MenuDTO> menuLst;
	
	
	
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:menu";
	}
	
	
	
	
	
	
	// 메뉴시퀀스로 한개만 가져오기
	@GetMapping("menudetail/{menuseq}")
	public Optional<MenuDTO> menuDTO(@PathVariable Long menuseq) {
		
		menuDTOselected=null;
		menuRepository.findById(menuseq).ifPresent(menu->{
			menuDTOselected = menuRepository.findById(menuseq);
			}); 			
			
		return menuDTOselected;
	}
	
	
	
	// 메뉴리스트 조회
	@GetMapping("/menulist/{shopseq}")
	public List<MenuDTO> menuList(@PathVariable Long shopseq) {
		
		List<MenuDTO> menuList = new ArrayList<>();
		
		shopRepository.findById(shopseq).ifPresent(shop->{
			
			menuRepository.findByShopAndMenuStatusNotOrderByMenuSeqDesc(shop, MenuStatus.비활성).forEach(s->{
				
				menuList.add(s);
			});
			
		});
		
		return menuList;
	}
	
	
	// 메뉴카테고리 조회
	@GetMapping("/menucategory/{shopseq}")
	public List<MenuCategoryDTO> menuCategoryList(@PathVariable Long shopseq) {
		
		List<MenuCategoryDTO> menuCategoryList = new ArrayList<>();

		started = false;
		shopRepository.findById(shopseq).ifPresent(shop->{
			
			if(started==false) {
				if(menuCategoryRepository.countByShop(shop)==0) {
					MenuCategoryDTO menuCategoryDTO = MenuCategoryDTO.builder()
							.menuCategoryName("카테고리없음")
							.menuCategoryOrder(0)
							.shop(shop)
							.build();
					menuCategoryRepository.save(menuCategoryDTO);
				}
				started=true;
			}
			
			menuCategoryRepository.findByShopAndMenuCategoryOrderNotOrderByMenuCategoryOrderAsc(shop, 0).forEach(sc->{
				
				menuCategoryList.add(sc);
			});
			
		});
		return menuCategoryList;
	}
	
	
	
	// 특정 메뉴카테고리 안의 메뉴이름 조회
	@GetMapping("/menucategoryview/{cno}")
	public List<MenuDTO> menuSelectByCategory(@PathVariable Long cno) {
			
		menuListByCategory=null;
		menuCategoryRepository.findById(cno).ifPresent(category->{			
			menuListByCategory = menuRepository.findByMenuCategoryOrderByMenuSeq(category);
		});

		return menuListByCategory;
	}
	
	
	// 카테고리 등록페이지
	@PostMapping("/menucategory/{shopseq}")
	public String categoryInsertPost(@PathVariable Long shopseq , 
									@RequestParam(defaultValue = "") String menuCategoryName 
									
									) {
		tempShop = null;
		shopRepository.findById(shopseq).ifPresent(shop->{
			tempShop=shop;
		});
		
		Long categoryCount = menuCategoryRepository.countByShop(tempShop);
		
		System.out.println("tempShop : " + tempShop);
		
		System.out.println("categoryCount : " + categoryCount);

		MenuCategoryDTO menuCategory = MenuCategoryDTO.builder()
				.menuCategoryName(menuCategoryName)
				.menuCategoryOrder(Integer.parseInt(categoryCount.toString()))
				.shop(tempShop).build();
		
		if(menuCategory.getMenuCategoryName()==null||menuCategory.getMenuCategoryName()=="") return "카테고리 이름을 입력해주세요.";
		if(!Pattern.matches("^[가-힣]{2,16}$", menuCategory.getMenuCategoryName())) return "카테고리 이름은 한글 2~16자로 작성해주세요.";	
		
		
		MenuCategoryDTO result = menuCategoryRepository.save(menuCategory);
		
		log.info("[카테고리등록]:" + result.toString());
		
		return "새 카테고리가 등록되었습니다.";
	}
	
	
	
	
	
	// 메뉴등록페이지
	@PostMapping("/menuInsert/{shopseq}")
	public String menuInsertPost(@PathVariable Long shopseq , 
								@RequestParam(defaultValue = "0") Long menuCategorySeq,
								@RequestParam(defaultValue = "") String menuName,
								@RequestParam(defaultValue = "0") Integer menuPrice,
								@RequestParam(defaultValue = "") String menuImage,
								@RequestParam(defaultValue = "false") boolean menuTop,
								@RequestParam(defaultValue = "") String menuSimpleInfo,
								@RequestParam(defaultValue = "") String menuDetailInfo
								) {
		
		System.out.println("shopseq :" + shopseq);
		System.out.println("menuCategorySeq :" + menuCategorySeq);
		System.out.println("menuName :" + menuName);
		System.out.println("menuPrice :" + menuPrice);
		System.out.println("menuImage :" + menuImage);
		System.out.println("menuTop :" + menuTop);
		System.out.println("menuSimpleInfo :" + menuSimpleInfo);
		System.out.println("menuDetailInfo :" + menuDetailInfo);
		
//		ShopDTO shop = ShopDTO.builder().shopSeq(shopseq).build();
		
		tempMenuCategory = null;
		menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
			tempMenuCategory=menuCategory;
		});
		System.out.println(tempMenuCategory);
		
		insertResult="";
		
		tempShop = null;
		shopRepository.findById(shopseq).ifPresent(shop->{
			tempShop=shop;
		});
		System.out.println(tempShop);
		
		
		if(menuCategorySeq==0) {
			tempMenuCategory=menuCategoryRepository.findByShopAndMenuCategoryOrder(tempShop, 0).get(0);
		}
		System.out.println(tempMenuCategory);
				
		MenuDTO menuDTO = MenuDTO.builder()
				.menuCategory(tempMenuCategory)
				.shop(tempShop)
				.menuName(menuName)
				.menuPrice(menuPrice)
				.menuImage(menuImage)
				.menuTop(menuTop)
				.menuSimpleInfo(menuSimpleInfo)
				.menuDetailInfo(menuDetailInfo)
				.menuStatus(MenuStatus.활성)
				.build();	
		
		if(menuDTO.getMenuName()==null||menuDTO.getMenuName()=="") return "메뉴 이름을 입력해주세요.";
		if(!Pattern.matches("^[가-힣]{2,16}$", menuDTO.getMenuName())) return "메뉴 이름은 한글 2~16자로 작성해주세요.";	
		
		if(!(0<=menuDTO.getMenuPrice() && menuDTO.getMenuPrice()<=99999999)) return "올바른 가격을 입력해주세요.";
		
		MenuDTO menu = MenuDTO.builder()
				
				.shop(shopRepository.findById(shopseq).get())
				.menuCategory(tempMenuCategory)
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
	
	
	// 메뉴삭제(비활성화)
	@PutMapping("/menuDelete")
	public String MenuDeletePost(@RequestParam(defaultValue = "0") Long menuSeq) {
		
		System.out.println(menuSeq);
		System.out.println(menuSeq.getClass().getName());

		if(menuSeq==0L) return "메뉴 아이디가 존재하지 않습니다.";
				
		menuRepository.findById(menuSeq).ifPresent(menu->{
			
			menu.setMenuStatus(MenuStatus.비활성);
			
			menuRepository.save(menu);
			
		});
		return "메뉴가 삭제되었습니다.";
	}
	
	
	
	
	
	// 메뉴업데이트(설명만)
	@PutMapping("/menuUpdateInfo")
	public String MenuUpdateInfo(@RequestParam(defaultValue = "0") Long menuSeq, 
								@RequestParam(defaultValue = "") String menuSimpleInfo,
								@RequestParam(defaultValue = "") String menuDetailInfo) {
		
		System.out.println(menuSeq);
		
		if(menuSeq==0L) return "메뉴 아이디가 존재하지 않습니다.";
		
		menuRepository.findById(menuSeq).ifPresent(menu->{
			
			menu.setMenuSimpleInfo(menuSimpleInfo);
			menu.setMenuDetailInfo(menuDetailInfo);
			
			menuRepository.save(menu);
			
		});
		
		return "메뉴 정보를 수정하였습니다.";
	}
	
	
	
	// 메뉴카테고리 변경
	@PutMapping("/menuUpdateCategory")
	public String MenuUpdateCategory(@RequestParam(defaultValue = "0") Long menuSeq, 
									@RequestParam(defaultValue = "0") Long menuCategorySeq) {
		
		if(menuSeq==0L) return "메뉴 아이디가 존재하지 않습니다.";
		
		tempMenuCategory = null;
		menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
			tempMenuCategory = menuCategory;
		});
		System.out.println(tempMenuCategory);
		
		menuRepository.findById(menuSeq).ifPresent(menu->{
			
			menu.setMenuCategory(tempMenuCategory);
			
			menuRepository.save(menu);
			
		});
		
		return "메뉴 카테고리를 변경했습니다.";
	}
	
	
	// 메뉴카테고리 순서 위에거랑 바꿈(선택된게 2, 위에거는 1)
	@PutMapping("/menuCategoryUp")
	public String MenuCategoryOrderChangeUp(@RequestParam(defaultValue = "0") Long menuCategorySeq ) {	
		
		if(menuCategorySeq==0) return "입력값이 없습니다." ;
		
		menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
			ShopDTO shop = menuCategory.getShop();
			int menuCategoryOrder = menuCategory.getMenuCategoryOrder();
			menuCategoryOrder = menuCategoryOrder>1?menuCategoryOrder-1:menuCategoryOrder;
			menuCategory.setMenuCategoryOrder(menuCategoryOrder);

			List<MenuCategoryDTO> swapTargetList = menuCategoryRepository.findByShopAndMenuCategoryOrder(shop, menuCategoryOrder);
			if(swapTargetList.size()==1&&!menuCategory.equals(swapTargetList.get(0))) {
				MenuCategoryDTO swapTarget = swapTargetList.get(0);
				swapTarget.setMenuCategoryOrder(swapTarget.getMenuCategoryOrder()+1);
				menuCategoryRepository.save(menuCategory);
				menuCategoryRepository.save(swapTarget);
			}
		});
		
		return "성공" ;
	}
	
	
	// 메뉴카테고리 순서 아래거랑 바꿈(선택된게 2, 아래거는 3)
		@PutMapping("/menuCategoryDown")
		public String MenuCategoryOrderChangeDown(@RequestParam(defaultValue = "0") Long menuCategorySeq ) {	
			
			if(menuCategorySeq==0) return "입력값이 없습니다." ;
			
			menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
				ShopDTO shop = menuCategory.getShop();
				int menuCategoryOrder = menuCategory.getMenuCategoryOrder();
				menuCategoryOrder = menuCategoryOrder<menuCategoryRepository.countByShop(shop)-1?menuCategoryOrder+1:menuCategoryOrder;
				menuCategory.setMenuCategoryOrder(menuCategoryOrder);

				List<MenuCategoryDTO> swapTargetList = menuCategoryRepository.findByShopAndMenuCategoryOrder(shop, menuCategoryOrder);
				if(swapTargetList.size()==1&&!menuCategory.equals(swapTargetList.get(0))) {
					MenuCategoryDTO swapTarget = swapTargetList.get(0);
					swapTarget.setMenuCategoryOrder(swapTarget.getMenuCategoryOrder()-1);
					menuCategoryRepository.save(menuCategory);
					menuCategoryRepository.save(swapTarget);
				}
			});
			
			return "성공" ;
		}
	
	
		
	// 메뉴카테고리 삭제(카테고리를 삭제한뒤 기존에 해당되던 메뉴들을 카테고리0번으로 바꾸고 삭제한 카테고리보다 순서가 크면 전부 1씩 빼준다)
		
		/*
		@PutMapping("/menuCategoryDelete")
		public String MenuCategoryDelete(@RequestParam(defaultValue = "0") Long menuCategorySeq ) {
											
			
			if(menuCategorySeq==0) return "입력값이 없습니다." ;
			
			menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
				
				ShopDTO shop = menuCategory.getShop();
				int menuCategoryOrder = menuCategory.getMenuCategoryOrder();
				
				MenuCategoryDTO menuCategoryDefault = menuCategory;
				
				menuCategoryRepository.findByShopAndMenuCategoryOrderGreaterThan(shop, menuCategoryOrder).forEach(greater ->{
					
					int newCategoryOrder = menuCategoryOrder - 1;
					
					greater.setMenuCategoryOrder(newCategoryOrder);
					
					menuCategoryRepository.save(greater);
				});
				
				
				menuCategoryRepository.findByShopAndMenuCategoryName(shop, "카테고리없음").forEach(i -> {
					
					Long categoryZero = i.getMenuCategorySeq();

					System.out.println(categoryZero);
					
					//MenuCategoryDTO menuCategoryUpdate= MenuCategoryDTO.builder().menuCategorySeq(categoryDefault).build();
					
					
					menuCategoryRepository.findById(categoryZero).ifPresent(zero -> {
						
						menuRepository.findByMenuCategoryOrderByMenuSeq(menuCategoryDefault).forEach(menu -> {
							menu.setMenuCategory(zero);

							menuRepository.save(menu);
						});
						menuCategoryRepository.findById(menuCategorySeq).ifPresent(category -> {
							
							menuCategoryRepository.deleteById(menuCategorySeq);
						});
						
					});
				
				});
				
			});
			
			return "카테고리를 삭제했습니다.";
		}
		*/
	
		@PutMapping("/menuCategoryDelete")
		public String MenuCategoryDelete(@RequestParam(defaultValue = "0") Long menuCategorySeq ) {
											
			
			if(menuCategorySeq==0) return "입력값이 없습니다." ;
			
			tempMenuCategory = null;
			menuCategoryRepository.findById(menuCategorySeq).ifPresent(menuCategory->{
				tempMenuCategory = menuCategory;				
			});
			
			if(tempMenuCategory==null) return "해당 카테고리가 없습니다.";
			
			menuLst = menuRepository.findByMenuCategoryOrderByMenuSeq(tempMenuCategory);
			
			tempShop =  tempMenuCategory.getShop();
			
			menuLst.forEach(menu->{
				
				ShopDTO shop = menu.getShop();
				MenuCategoryDTO menuCategory = menuCategoryRepository.findByShopAndMenuCategoryOrder(shop, 0).get(0);
				menu.setMenuCategory(menuCategory);
				menuRepository.save(menu);

			});
			
			List<MenuCategoryDTO> menuCategoryList = menuCategoryRepository.findByShopAndMenuCategoryOrderGreaterThan(tempShop,tempMenuCategory.getMenuCategoryOrder());
			
			menuCategoryList.forEach(menuCategory->{
				menuCategory.setMenuCategoryOrder(menuCategory.getMenuCategoryOrder()-1);
				menuCategoryRepository.save(menuCategory);
			});
			
			menuCategoryRepository.delete(tempMenuCategory);
			
			return "카테고리를 삭제했습니다.";
		}
	
	
	
	
	
	
	
	
	
	
	
}
