package com.zumuniyo.main.dto;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
public class FavoritesDTO {

	@EmbeddedId
	FavoritesKey favoriteId;
	
}
