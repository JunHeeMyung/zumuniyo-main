package com.zumuniyo.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.zumuniyo.main.dto.MenuDTO;

public interface MenuRepository extends CrudRepository<MenuDTO, Long>{

}
