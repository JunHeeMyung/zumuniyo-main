package com.zumuniyo.main.dto;

import java.sql.Timestamp;

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
@Table(name="coupon")
@Entity
@Builder
@EqualsAndHashCode(of = "couponSeq")
public class CouponDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long couponSeq;
	
	@ManyToOne
	private MemberDTO member;
	
	@ManyToOne
	private ShopDTO shop;
	
	private int couponMinCond;
	private int couponDC;
	
	@CreationTimestamp
	private Timestamp couponRegdate;
	
	private Timestamp couponExpire;
	
	@ManyToOne
	private OrderGroupDTO orderGroup;
	
}