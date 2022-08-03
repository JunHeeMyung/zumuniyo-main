package com.zumuniyo.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.dto.ReviewDTO;
import com.zumuniyo.main.repository.MemberRepository;
import com.zumuniyo.main.repository.MenuRepository;
import com.zumuniyo.main.repository.ReviewRepository;
import com.zumuniyo.main.repository.ShopRepository;

@RestController
@RequestMapping("/review/*")
public class ReviewController {

	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	MemberRepository memRepo;

//	@Autowired
//	ShopRepository shopRepo;	
//	
//	@Autowired
//	MenuRepository menuRepo;

//	ReviewDTO upReview = ReviewDTO.builder().build();
	List<String> imgs = new ArrayList<>();

	@GetMapping("/reviewTest")
	public String reviewTest() {
		return "도착";
	}

	@GetMapping("/reviewList")
	public List<ReviewDTO> reviewList() {
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo
				.findAll(Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		return reviewList;
	}

	// 미구현
	@GetMapping("/reviewShopList/{shopseq}")
	public List<ReviewDTO> reviewShopList(@PathVariable Long shopseq) {
		// 샵의 리뷰 미구현
		System.out.println("shopseq :" + shopseq);
//		shopRepo.findById(shopseq);

		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo
				.findAll(Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		return reviewList;
	}

	// 미구현
	@GetMapping("/reviewMenuList/{menuseq}")
	public List<ReviewDTO> reviewMenuList(@PathVariable Long menuseq) {
		// 메뉴의 리뷰 미구현
		System.out.println("menuseq :" + menuseq);
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo
				.findAll(Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		return reviewList;
	}

//	@PostMapping(value="/reviewInsert" , consumes = "application/json")	
	@PostMapping("/reviewInsert/{memNick}")
//	@PostMapping("/reviewInsert")
//	public void reviewInsert(ReviewDTO review) {	
//	public void reviewInsert(@RequestBody ReviewDTO review, @RequestBody  String memNick) {					
	public int reviewInsert(ReviewDTO review, @PathVariable String memNick) {

		review.setMember(memRepo.findByMemNick(memNick).get());
//		review.setMember(memRepo.findById(114l).get());
		review.setReviewExposure(false);
				
		
		if (imgs != null) {
			System.out.println("이미지 있음");
			review.setReviewImages(imgs);
		}				


		System.out.println(review);
//		reviewRepo.save(review);
		if (reviewRepo.save(review) != null) {
			return 1;
		} else {
			return -1;
		}
	}

	@PutMapping("/reviewUpdate/{bno}")
	public void reviewUpdate(@PathVariable Long bno, @RequestBody ReviewDTO review) {

		ReviewDTO reviewUpdate = reviewRepo.findById(bno).get();
		reviewUpdate = review;
		System.out.println(reviewUpdate);
//		reviewRepo.save(reviewUpdate);

	}

	@DeleteMapping("/reviewDelete/{bno}")
//	public String reviewDelete(@PathVariable Long bno)	{
	public String reviewDelete(@PathVariable Long bno, String memNick) {

		System.out.println("삭제요청옴");
		System.out.println("bno&memnick :" + bno + "&" + memNick);
		ReviewDTO reviewDelete = reviewRepo.findById(bno).get();

		String reviewnick = reviewDelete.getMember().getMemNick();
//		MemberDTO deletemem = memRepo.findByMemNick(memnick).get();		

		String dmem = memRepo.findByMemNick(memNick).get().getMemNick();
//		String dmem = deletemem.getMemNick();

		System.out.println("reviewnick&dmem :" + reviewnick + "&" + dmem);

		System.out.println(reviewDelete);

		if (reviewnick.equals(dmem)) {
			System.out.println("정보일치");
			reviewRepo.deleteById(bno);
			return "success";
		} else {
			System.out.println("불일치");
			return "fail";
		}

	}

	// @GetMapping("/upload")
	// public void reviewImg(String aa) throws Exception {
//	@CrossOrigin(origins = {"http://localhost:3000"})	
	@PostMapping("/upload")
//	public void reviewImg(@RequestParam MultipartFile file) throws Exception {
	public void reviewImg(@RequestParam MultipartFile file) throws Exception {
		System.out.println("요청들어옴");
//		String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		
		System.out.println(file);
		String rootPath = "C:/MSA/3Project/zumuniyo-react/public";		
		String basePath = rootPath + "/" + "img";
		String filePath = basePath + "/" + file.getOriginalFilename();
		File dest = new File(filePath);
		
		file.transferTo(dest); // 파일 업로드 작업 수행
		
		imgs.add(filePath);

	}


}
