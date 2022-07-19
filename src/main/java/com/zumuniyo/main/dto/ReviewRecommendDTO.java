package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private MemberDTO member;
	
	@Id
	@ManyToOne
	private ReviewDTO review;

}
