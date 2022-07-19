package com.zumuniyo.main.repository;

import org.springframework.data.repository.CrudRepository;

import com.zumuniyo.main.dto.MenuCategoryDTO;

public interface MenuCategoryRepository extends CrudRepository<MenuCategoryDTO, Long> {

}
