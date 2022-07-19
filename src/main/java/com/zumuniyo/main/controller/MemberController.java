package com.zumuniyo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.repository.MemberRepository;

import lombok.extern.java.Log;

@Log
@CrossOrigin
@RestController
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@GetMapping("/test")
	public List<MemberDTO> hello() {
		
		log.info("test");
		
		List<MemberDTO> memberList = (List<MemberDTO>) memberRepository.findAll();
		
		return memberList;
	}

}
