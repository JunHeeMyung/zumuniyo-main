package com.zumuniyo.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zumuniyo.main.dto.ShopCategory;
import com.zumuniyo.main.dto.ShopDTO;

public interface SearchRepository extends CrudRepository<ShopDTO, Long> {
		//재업로드
	@Query(value="select * from shop where shop_name like '%'||?1||'%' or  shop_info like '%'||?2||'%' or shop_category =  ?3 ", nativeQuery = true )
	List<ShopDTO> findByShopNameContainingOrShopInfoContainingOrShopCategory(String a, String b, String c);
}
