package com.zumuniyo.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.repository.CommonRepository;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	CommonRepository commonRepository;
	
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:main";
	}
	
	@GetMapping("/shopmapdata")
	public List<Map<String,Object>> getShopMapData() {
		return commonRepository.getShopMapData();
	}

	
}