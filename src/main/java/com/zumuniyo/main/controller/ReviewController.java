package com.zumuniyo.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zumuniyo.main.dto.ReviewDTO;
import com.zumuniyo.main.repository.ReviewRepository;

@RestController
@RequestMapping("/review/*")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	
	@GetMapping("/reviewList")
	public List<ReviewDTO> reviewList() {
		
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo.findAll();
		
		
		return reviewList;
	}

}
