package com.zumuniyo.main.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FavoritesKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private MemberDTO member;
	@ManyToOne
	private ShopDTO shop;

}