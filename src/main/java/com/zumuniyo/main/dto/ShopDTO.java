package com.zumuniyo.main.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name="shop")
@Entity
@Builder
@EqualsAndHashCode(of = "shopSeq")
public class ShopDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long shopSeq;
	
	private String shopName;
	
	@ManyToOne
	private LocationDTO location;
	
	private String shopAddrDetail;
	
	@ManyToOne
	@JsonIgnore
	private MemberDTO member;
	
	@Enumerated(EnumType.STRING)
	private ShopCategory shopCategory;
	
	private String shopLogo;
	
	@ElementCollection(targetClass=String.class)
	private List<String> shopImages;
	
	private String shopInfo;
	private String shopNotice;
	private String shopDetail;
	
	@CreationTimestamp
	private Timestamp shopRegdate;
	
	@Enumerated(EnumType.STRING)
	private ShopStatus shopStatus;
	
	@JsonProperty
	public Long getMember() {
		return member.getMemSeq();
	}

}
