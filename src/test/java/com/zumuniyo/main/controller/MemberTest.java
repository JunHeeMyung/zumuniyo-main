package com.zumuniyo.main.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.Memstatus;
import com.zumuniyo.main.dto.Memtype;
import com.zumuniyo.main.repository.MemberRepository;


@SpringBootTest
public class MemberTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	//@Test
	public void register() {
		
		
		
		
		MemberDTO m = MemberDTO.builder().memEmail("test@naver.com").memType(Memtype.일반회원).build();
		
		System.out.println("입력이메일:"+m.getMemEmail());
		
		memberRepository.findByMemEmailAndSocialType(m.getMemEmail(), "kakao")
		
					.ifPresentOrElse(_member->{
							System.out.println(_member);
							System.out.println("이미 존재하는 아이디입니다");
						},()->{
							MemberDTO member = MemberDTO.builder().memEmail(m.getMemEmail())
									.memStatus(Memstatus.활성)
									.memType(m.getMemType())
									.socialType("kakao")
									.build();
				
							MemberDTO result = memberRepository.save(member);
							
							System.out.println("등록됨: "+result.toString());
						});
		return;
	}
	
	

}
