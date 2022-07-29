package com.zumuniyo.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.zumuniyo.main.dto.ReviewDTO;
import com.zumuniyo.main.repository.MemberRepository;
import com.zumuniyo.main.repository.ReviewRepository;

@RestController
@RequestMapping("/review/*")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired
	MemberRepository memRepo;
	
//	ReviewDTO upReview = new ReviewDTO();
	ReviewDTO upReview = ReviewDTO.builder().build();
	
	
	@GetMapping("/reviewList")
	public List<ReviewDTO> reviewList() {
		
//		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo.findAll();		
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo.findAll(Sort.by(Sort.Direction.DESC,"reviewRegdate"));
		
		return reviewList;
	}
	
	@GetMapping("/reviewTest")
	public String reviewTest() {		
		
		return "도착";
	}
	
	
	

//	@PostMapping(value="/reviewInsert" , consumes = "application/json")	
	@PostMapping("/reviewInsert")
	public void reviewInsert(@RequestBody  ReviewDTO review) {	
				
		review.setMember(memRepo.findById(1l).get());
		review.setReviewExposure(false);
		if(upReview!=null) {
			System.out.println("이미지 있음");
			review.setReviewImages(upReview.getReviewImages());
		}
		
//		review = ReviewDTO.builder()
//					.member(memRepo.findById(1l).get())						
//					.reviewImages(upReview.getReviewImages())
//					.build();;
		System.out.println(review);		
		reviewRepo.save(review);
	}
	
	
	@PutMapping("/reviewUpdate/{bno}")
	public void reviewUpdate(@PathVariable Long bno, @RequestBody ReviewDTO review) {
		
		ReviewDTO reviewUpdate = reviewRepo.findById(bno).get();
		reviewUpdate = review;
		System.out.println(reviewUpdate);
//		reviewRepo.save(reviewUpdate);
		
	}
	
	@DeleteMapping("/reviewDelete/{bno}")
	public void reviewDelete(@PathVariable Long bno)	{
		System.out.println("삭제요청옴");
		ReviewDTO reviewDelete = reviewRepo.findById(bno).get();
		System.out.println(reviewDelete);
		
		reviewRepo.deleteById(bno);
		
	}
	

	//@GetMapping("/upload")
	//public void reviewImg(String aa) throws Exception {
	@CrossOrigin(origins = {"http://localhost:3000"})	
	@PostMapping("/upload")
	public void reviewImg(@RequestParam MultipartFile file) throws Exception {
		System.out.println("요청들어옴");
		
//		String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
//		String testPath = "C:/MSA/3Project/zumuniyo-main/src/main/resources/img";
		
	//	String testPath = "C:/MSA/reacttest/forreact/public";
		String testPath = "C:/MSA/3Project/zumuniyo-react/public";
		
		System.out.println(file);
		String rootPath = testPath;
//		String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
	    String basePath = rootPath + "/" + "img";
	    String filePath = basePath + "/" + file.getOriginalFilename();
	    File dest = new File(filePath);
	    file.transferTo(dest); // 파일 업로드 작업 수행
	    
	    List<String> imgs = new ArrayList<>();
	    imgs.add(filePath);
	    
	    
//	    	upReview = ReviewDTO.builder()
//	    		.reviewImages(imgs)
//	    		.build();
	    	
	    upReview.setReviewImages(imgs);
	    System.out.println(upReview);
	   // reviewRepo.save(reviewImg);
	   // reviewRepo.save(upReview);
	   // return file.getOriginalFilename();
	    
	}
	
	
	
//	@PostMapping("/reviewInsert")
//	@CrossOrigin(origins = {"http://localhost:3000"})
//	public void reviewInsert(@RequestBody  ReviewDTO review, @RequestParam MultipartFile file) throws Exception {	
//				
//		
//		if(file!=null) {
//			System.out.println("업로드파일들어옴");
//			String testPath = "C:/MSA/3Project/zumuniyo-main/src/main/resources/img";
//    //  	String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
//			String rootPath = testPath;
//		    String basePath = rootPath + "/" + "review";
//		    String filePath = basePath + "/" + file.getOriginalFilename();
//		    File dest = new File(filePath);
//		    file.transferTo(dest); // 파일 업로드 작업 수행
//		    
//		    List<String> imgs = new ArrayList<>();
//		    imgs.add(filePath);
//		    
//		    review = ReviewDTO.builder()
//					.member(memRepo.findById(1l).get())
//					.reviewImages(imgs)
//					.build();;
//			
//			reviewRepo.save(review);
//		    
//			
//		}else {
//			System.out.println("업로드파일 없음");
//			review = ReviewDTO.builder()
//					.member(memRepo.findById(1l).get())		
//					.build();;
//					
//			System.out.println(review);		
//			reviewRepo.save(review);		
//		}	
//	}
	
	
	
}
