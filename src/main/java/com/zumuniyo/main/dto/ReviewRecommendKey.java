package com.zumuniyo.main.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	@JsonIgnore
	private MemberDTO member;
	
	private ReviewDTO review;
	
	@JsonProperty
	public Long getMember() {
		return member==null?null:member.getMemSeq();
	}
	
}
