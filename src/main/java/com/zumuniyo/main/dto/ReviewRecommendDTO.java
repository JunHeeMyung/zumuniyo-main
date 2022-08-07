package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="reviewrecommend")
@Entity
@Builder
@IdClass(ReviewRecommendKey.class)
public class ReviewRecommendDTO {

	@Id
	@ManyToOne
	@JsonIgnore
	private MemberDTO member;
	
	@Id
	@ManyToOne
	private ReviewDTO review;
	
	@JsonProperty
	public Long getMember() {
		return member.getMemSeq();
	}

}
