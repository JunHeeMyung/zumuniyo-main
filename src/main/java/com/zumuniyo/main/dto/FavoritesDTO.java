package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Table(name="favorites")
@Entity
@Builder
@IdClass(FavoritesKey.class)
public class FavoritesDTO {

	@Id
	private MemberDTO member;
	
	@Id
	private ShopDTO shop;
	
}
