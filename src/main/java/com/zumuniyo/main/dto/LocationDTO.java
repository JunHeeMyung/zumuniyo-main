package com.zumuniyo.main.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name="location")
@Entity
@Builder
@EqualsAndHashCode(of = "locAddr")
public class LocationDTO {
	
	@Id
	private String locAddr;
	
	private double locLat;
	private double locLon;

}
