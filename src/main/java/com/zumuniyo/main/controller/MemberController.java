package com.zumuniyo.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	String loginResult;

	/* 로그인 */
	@PostMapping("/login/naver")
	public String login(	@RequestParam(defaultValue = "") String memEmail,
							@RequestParam(defaultValue = "") String memToken,
							HttpServletRequest request) {
		
		/* 입력검증 */
		if(memEmail.equals("")||memToken.equals("")) return "입력값없음";
		
		/* 요청검증 */
		String token = memToken; // 네이버 로그인 접근 토큰;
	    String header = "Bearer " + token; // Bearer 다음에 공백 추가
	    String apiURL = "https://openapi.naver.com/v1/nid/me";
	    String email = "";
	    Map<String, String> requestHeaders = new HashMap<>();
	    requestHeaders.put("Authorization", header);
	    String responseBody = com.zumuniyo.main.NaverLogin.get(apiURL,requestHeaders);
	    
	    /* 받은 토큰으로 이메일 검증 */
	    try {
	    	JSONObject jsonObject = new JSONObject(responseBody);
	    	email = (new JSONObject(jsonObject.get("response").toString())).get("email").toString(); 
	    } catch (Exception e) {
	    	log.info("[naver:"+memEmail+"] 토큰 검증 파싱 에러");
	    }
	    
		/* 토큰값 다를 경우 */
	    if(!email.equals(memEmail)) return "토큰이상";
	    
	    /* 아이디요청 */
		memberRepository.findByMemEmailAndSocialType(memEmail, "naver").ifPresentOrElse(member->{
			ObjectMapper om = new ObjectMapper();
			try 
			{
				if(member.getMemStatus()==Memstatus.활성) {
					loginResult = om.writeValueAsString(member);
					/* 세션저장 */
					request.getSession().setAttribute("member", member);
				}else {
					loginResult="로그인실패";
				}
			} catch (JsonProcessingException e) {
				log.info("[naver:"+memEmail+"] 로그인 요청 파싱 에러");
			}
		}, ()->{
			loginResult = "아이디없음";
		});
		
		log.info("[naver:"+memEmail+"] 로그인 결과: "+loginResult);
		return loginResult;
	}
	
	/* 로그아웃 */
	@PostMapping("/logout")
	public boolean logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return true;
	}
	
	/* 로그인여부 */
	@GetMapping("/login")
	public boolean isLogined(HttpServletRequest request) {
		return request.getSession().getAttribute("member")!=null;
	}
	
	
	
	
	
	
	/* 네이버 소셜회원가입 */
	@PostMapping("/naver")
	public MemberDTO register(MemberDTO m) {
		
		MemberDTO member = MemberDTO.builder()
							.memEmail(m.getMemEmail())
							.memStatus(Memstatus.활성)
							.memNick(m.getMemNick())
							.memType(m.getMemType())
							.socialType("naver")
							.build();
		
		MemberDTO result = memberRepository.save(member);
		log.info("[회원가입]:"+result.toString());
		return result;
		
	}
	
	
	

}
