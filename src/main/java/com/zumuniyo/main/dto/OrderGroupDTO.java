package com.zumuniyo.main.dto;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

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
@Table(name="ordergroup")
@Entity
@Builder
@EqualsAndHashCode(of = "orderGroupSeq")
public class OrderGroupDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderGroupSeq;
	
	int tableNum;
	
	@ManyToOne
	private ShopDTO shop;
	
	@CreationTimestamp
	private Timestamp orderGroupRegdate;
	
	@ManyToOne
	@JsonIgnore
	private MemberDTO member;
	
	OrderStatus orderStatus;
	
	@Transient
	@JsonIgnore
	Map<String,Object> memberJSON;
	
	@JsonProperty(value = "member")
	public Map<String,Object> getMember() {
		memberJSON = new HashMap<String,Object>();
		memberJSON.put("memSeq", member==null?null:member.getMemSeq());
		memberJSON.put("memNick", member==null?null:member.getMemNick());
		return memberJSON;
	}

}
