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
	private MemberDTO member;
	
	OrderStatus orderStatus; 

}
