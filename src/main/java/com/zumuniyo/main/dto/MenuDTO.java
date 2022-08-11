package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@ToString(exclude = {"shop","menuCategory"})
@Table(name="menu")
@Entity
@Builder
@EqualsAndHashCode(of = "menuSeq")
public class MenuDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long menuSeq;
	
	@ManyToOne
	private MenuCategoryDTO menuCategory;
	
	@ManyToOne
	private ShopDTO shop;
	
	private String menuName;
	private int menuPrice; 
	
	private String menuImage;
	
	private boolean menuTop;
	
	private String menuSimpleInfo;
	private String menuDetailInfo;
	
	@Enumerated(EnumType.STRING)
	private MenuStatus menuStatus;

}
