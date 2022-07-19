package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name="zorder")
@Entity
@Builder
@EqualsAndHashCode(of = "orderSeq")
public class OrderDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderSeq;
	
	@ManyToOne
	private MenuDTO menu;
	
	private int count;

	@ManyToOne
	private OrderGroupDTO orderGroup;
	
}
