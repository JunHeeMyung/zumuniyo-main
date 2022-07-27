package com.zumuniyo.main.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name="noticeboard")
@Entity
@Builder
@EqualsAndHashCode(of = "noticeBoardSeq")
public class NoticeBoardDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noticeBoardSeq;
	
	private String title;
	private String writer;
	
	@CreationTimestamp
	private Timestamp regdate;
	
	@UpdateTimestamp 
	private Timestamp updatedate;
	
	private int hitCount;
	
	private String content;
	
	@ElementCollection(targetClass=String.class)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "noticeNo") 
	private List<NoticeAttachment> images;	
	
	private boolean boardTop;
	 
}
