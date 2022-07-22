package com.zumuniyo.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.Memstatus;
import com.zumuniyo.main.repository.MemberRepository;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberRepository memberRepository;

		
	@GetMapping("/heartbeat")
	public String heartbeat() {
		return "heartbeat:member";
	}
	
	@PostMapping("/kakao")
	public MemberDTO register(MemberDTO m) {
		
		MemberDTO member = MemberDTO.builder().memEmail(m.getMemEmail())
							.memStatus(Memstatus.활성)
							.memType(m.getMemType())
							.socialType("kakao")
							.build();
		
		MemberDTO result = memberRepository.save(member);
		log.info(result.toString());
		
		return result;
		
	}
	

}
