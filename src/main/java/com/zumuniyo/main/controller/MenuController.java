package com.zumuniyo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MenuDTO;
import com.zumuniyo.main.repository.MenuRepository;

import lombok.extern.java.Log;


@Log
@CrossOrigin
@RestController
@RequestMapping("/menu/*")
public class MenuController {

	@Autowired
	MenuRepository menuRepository;
	
	@GetMapping("/test")
	public List<MenuDTO> helloMenu() {
		
		log.info("test");
		
		List<MenuDTO> menuList = (List<MenuDTO>) menuRepository.findAll();
		System.out.println("test");
		return menuList;
	}
	
	
	
	
	
}
