package com.zumuniyo.main.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="review")
@Entity
@Builder
@EqualsAndHashCode(of = "reviewSeq")
public class ReviewDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reviewSeq;
	
	@ManyToOne
	private MemberDTO member;
	
	@ManyToOne
	private OrderGroupDTO orderGroup;
	
	private float reviewTaste;
	private float reviewAmount;
	private float reviewService;
	
	private String reviewContent;
	
	@ElementCollection(targetClass=String.class)
	private List<String> reviewImages;
	
	@CreationTimestamp
	private Timestamp reviewRegdate;
	
	private boolean reviewExposure;

}
