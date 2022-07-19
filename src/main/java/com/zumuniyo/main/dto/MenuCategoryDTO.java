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
@Table(name="menucategory")
@Entity
@Builder
@EqualsAndHashCode(of = "menuCategorySeq")
public class MenuCategoryDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long menuCategorySeq;
	
	private String menuCategoryName;
	private int menuCategoryOrder;
	
	@ManyToOne
	private ShopDTO shop;

}
