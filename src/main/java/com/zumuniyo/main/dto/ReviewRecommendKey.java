package com.zumuniyo.main.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecommendKey implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private MemberDTO member;
	private ReviewDTO review;
	
}
