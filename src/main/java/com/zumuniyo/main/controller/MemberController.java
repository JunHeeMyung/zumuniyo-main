package com.zumuniyo.main.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	String loginResult;
	String registerResult;

	/* 로그인 */
	@PostMapping("/login/naver")
	public String login(	@RequestParam(defaultValue = "") String memEmail,
							@RequestParam(defaultValue = "") String memToken,
							HttpServletRequest request) {
		
		loginResult = "";
		
		/* 입력검증 */
		if(memEmail==null||memToken==null||memEmail.equals("")||memToken.equals("")) return "입력값없음";
		
		/* 받은 토큰으로 이메일 검증 */
		String token = memToken; // 네이버 로그인 접근 토큰;
	    String header = "Bearer " + token; // Bearer 다음에 공백 추가
	    String apiURL = "https://openapi.naver.com/v1/nid/me";
	    String email = "";
	    
	    Map<String, String> requestHeaders = new HashMap<>();
	    requestHeaders.put("Authorization", header);
	    String responseBody = com.zumuniyo.main.NaverLogin.get(apiURL,requestHeaders);
	    
	    try {
	    	JSONObject jsonObject = new JSONObject(responseBody);
	    	email = (new JSONObject(jsonObject.get("response").toString())).get("email").toString(); 
	    } catch (Exception e) {
	    	log.info("[naver:"+memEmail+"] 토큰 검증 파싱 에러");
	    }
	    
		/* 토큰값 다를 경우 */
	    if(!email.equals(memEmail)) return "토큰이상";
	    
	    /* 가입여부 체크 */
		memberRepository.findByMemEmailAndSocialType(memEmail, "naver").ifPresentOrElse(member->{

				if(member.getMemStatus()==Memstatus.활성) {
					loginResult = "로그인성공";
					/* 세션저장 */
					request.getSession().setAttribute("member", member);
				}else {
					loginResult = "로그인실패";
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
	
	/* 현재로그인 상태 */
	@GetMapping("/login")
	public boolean isLogined(HttpServletRequest request) {
		return request.getSession().getAttribute("member")!=null;
	}
	
	/* 현재로그인 닉네임 */
	@GetMapping("/nick")
	public String getMemNick(HttpServletRequest request) {
		if(request.getSession().getAttribute("member")!=null) {
			return ((MemberDTO)request.getSession().getAttribute("member")).getMemNick();
		}
		return "";
	}
	
	/* 현재로그인 타입 */
	@GetMapping("/type")
	public String getMemType(HttpServletRequest request) {
		if(request.getSession().getAttribute("member")!=null) {
			return ((MemberDTO)request.getSession().getAttribute("member")).getMemType().toString();
		}
		return "";
	}
	
	
	/* 네이버 소셜회원가입 */
	@PostMapping("/naver")
	public String register(MemberDTO m) {

		registerResult="";
		
		/* 입력검증 */
		if(m.getMemEmail()==null||m.getMemEmail()=="") return "이메일이 잘못되었습니다";
		
		/* 기회원 */
		memberRepository.findByMemEmailAndSocialType(m.getMemEmail(), "naver").ifPresent(member->{
			registerResult="이미 가입된 회원입니다";
		});
		if(!registerResult.equals("")) return registerResult;
		
		/* 닉네임체크 */
		if(!Pattern.matches("^[가-힣]{2,8}$", m.getMemNick())) return "닉네임이 올바르지 않습니다 (한글 2~8자)";
		
		memberRepository.findByMemNick(m.getMemNick()).ifPresent(member->{
			registerResult="사용중인 닉네임 입니다";
		});
		if(!registerResult.equals("")) return registerResult;
		
		/* 회원가입 */
		MemberDTO member = MemberDTO.builder()
							.memEmail(m.getMemEmail())
							.memStatus(Memstatus.활성)
							.memNick(m.getMemNick())
							.memType(m.getMemType())
							.socialType("naver")
							.build();
		
		MemberDTO result = memberRepository.save(member);
		
		log.info("[회원가입]:"+result.toString());
		
		return "가입성공";
		
	}

}
