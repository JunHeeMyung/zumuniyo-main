package com.zumuniyo.main.controller;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zumuniyo.main.dto.NoticeBoardDTO;
import com.zumuniyo.main.repository.NoticeBoardRepository;


@SpringBootTest
public class NoticeTest {
	
	@Autowired
	NoticeBoardRepository Nrepo;
	

	/*@Test
	public void insert() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			
			NoticeBoardDTO Nboard = NoticeBoardDTO.builder()
					.title("화요일"+i)
					.writer("hong")  //0~4
					.content("공지사항입니다"+i)
					.build();
		Nrepo.save(Nboard);
	});*/
//	}
}