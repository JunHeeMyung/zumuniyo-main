package com.zumuniyo.main.dto;

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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="NoticeAttachment")
@EqualsAndHashCode(of="bno")
public class NoticeAttachment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long noticeAttachSeq;
	private String fileName;
	
	
}
