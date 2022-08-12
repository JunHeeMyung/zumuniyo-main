package com.zumuniyo.main.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NoticeBoardService {
	
	@Autowired
	NoticeBoardRepository boardRepo;
	 
	@Transactional    
	 public int updateView(Long NoticeBoardSeq) 
	 {        return boardRepo.updateHitCount(NoticeBoardSeq);    }




}
