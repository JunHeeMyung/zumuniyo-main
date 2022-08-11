package com.zumuniyo.main.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.CommonRepository;
import com.zumuniyo.main.repository.ShopRepository;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	CommonRepository commonRepository;
	
	@Autowired
	ShopRepository shopRepository;
	
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:main";
	}
	
	@GetMapping("/shopmapdata")
	public List<Map<String,Object>> getShopMapData() {
		return commonRepository.getShopMapData();
	}
	
	@GetMapping("/manager")
	public List<ShopDTO> getShopListForManager(HttpServletRequest request){
		
		MemberDTO loginedMember = ((MemberDTO)request.getSession().getAttribute("member"));
		if(loginedMember==null) return null;
		
		return shopRepository.findByMemberOrderByShopSeqDesc(loginedMember);
	}
	
	
	@GetMapping("/")
	public String gettest(@RequestParam(defaultValue = "get:id없음") String id,
							@RequestParam(defaultValue = "get:pw없음") String pw,
							@RequestParam(defaultValue = "get:tel없음") String tel,
						HttpServletRequest request
						){
		
		System.out.println(id);
		System.out.println(pw);
		System.out.println(tel);
		
		return "GetMapping수신완료";
	}
	
	@PostMapping("/")
	public String posttest(@RequestParam(defaultValue = "post:id없음") String id,
						@RequestParam(defaultValue = "post:pw없음") String pw,
						@RequestParam(defaultValue = "post없음") String tel,
						HttpServletRequest request
						){
		
		System.out.println(id);
		System.out.println(pw);
		System.out.println(tel);
		
		return "PostMapping수신완료";
	}
	
	@PutMapping("/")
	public String puttest(@RequestParam(defaultValue = "put:id없음") String id,
							@RequestParam(defaultValue = "put:pw없음") String pw,
							@RequestParam(defaultValue = "put:tel없음") String tel,
						HttpServletRequest request
						){
		
		System.out.println(id);
		System.out.println(pw);
		System.out.println(tel);
		
		return "PutMapping수신완료";
	}
	
	@DeleteMapping("/")
	public String deletetest(@RequestParam(defaultValue = "put:id없음") String id,
							@RequestParam(defaultValue = "put:pw없음") String pw,
							@RequestParam(defaultValue = "put:tel없음") String tel,
						HttpServletRequest request
						){
		
		System.out.println(id);
		System.out.println(pw);
		System.out.println(tel);
		
		return "DeleteMapping수신완료";
	}
	
	
	
}