package com.zumuniyo.main.dto;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonIgnore
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
	
	@Transient
	@JsonIgnore
	Map<String,Object> memberJSON;
	
	@JsonProperty(value = "member")
	public Map<String,Object> getMember() {
		memberJSON = new HashMap<String,Object>();
		memberJSON.put("memSeq", member.getMemSeq());
		memberJSON.put("memNick", member.getMemNick());
		return memberJSON;
	}

}
