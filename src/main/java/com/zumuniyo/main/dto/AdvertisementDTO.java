package com.zumuniyo.main.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="advertisement")
@Entity
@Builder
@EqualsAndHashCode(of = "adSeq")
public class AdvertisementDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adSeq;
	
	private String owner;
	
	private String image;
	
	private Timestamp start;
	private Timestamp end;
	
	private int view;
	private int click;
	
}
