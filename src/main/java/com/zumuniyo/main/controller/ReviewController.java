package com.zumuniyo.main.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import com.zumuniyo.main.dto.OrderGroupDTO;
import com.zumuniyo.main.dto.ReviewDTO;
import com.zumuniyo.main.dto.ShopDTO;
import com.zumuniyo.main.repository.MemberRepository;
import com.zumuniyo.main.repository.MenuRepository;
import com.zumuniyo.main.repository.OrderGroupRepository;
import com.zumuniyo.main.repository.ReviewRepository;
import com.zumuniyo.main.repository.ShopRepository;

@RestController
@RequestMapping("/review/*")
public class ReviewController {

	@Autowired
	ReviewRepository reviewRepo;

	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	OrderGroupRepository orderGRepo;

	@Autowired
	ShopRepository shopRepo;	
//	
//	@Autowired
//	MenuRepository menuRepo;

	List<String> imgs = new ArrayList<>();

	
	
	
	//테스트용 메시지
	@GetMapping("/reviewTest")
	public String reviewTest() {
		return "도착";
	}

	
	
	
	//전체조회
	@GetMapping("/reviewList")
	public List<ReviewDTO> reviewList() {
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo
				.findAll(Sort.by(Sort.Direction.DESC, "reviewRegdate"));
		return reviewList;
	}

	//나의 리뷰 조회
	@GetMapping("/reviewMemList")
	public List<ReviewDTO> reviewMemList(HttpServletRequest request) {		
		List<ReviewDTO> reviewList = new ArrayList<>();
		if (request.getSession().getAttribute("member") != null) {
			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
			reviewList = reviewRepo.selectAllByMem(mem.getMemSeq());
			return reviewList;
		}
		return reviewList;
	}

	//샵의 리뷰 조회
	@GetMapping("/reviewShopList/{shopseq}")
	public List<ReviewDTO> reviewByShop(@PathVariable Long shopseq) {		
		System.out.println("shopseq :" + shopseq);
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo.selectAllByShop(shopseq);
		System.out.println("reviewList :" +reviewList);
		return reviewList;		
	}
	

	//메뉴의 리뷰 조회
	@GetMapping("/reviewMenuList/{menuseq}")
	public List<ReviewDTO> reviewByMenu(@PathVariable Long menuseq) {		
		System.out.println("menuseq :" + menuseq);
		List<ReviewDTO> reviewList = (List<ReviewDTO>) reviewRepo.selectAllBymenu(menuseq);
		return reviewList;
	}
		
	

	
	
	//리뷰 등록
	@PostMapping("/reviewInsert")
	public int reviewInsert(ReviewDTO review, HttpServletRequest request) {

		System.out.println("리뷰등록요청");
		if (request.getSession().getAttribute("member") != null) {
			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
			System.out.println(mem);

			review.setMember(mem);
			review.setReviewExposure(false);

			if (imgs != null) {
				System.out.println("이미지 있음");
				review.setReviewImages(imgs);
			}
			System.out.println(review);
			if (reviewRepo.save(review) != null) {
				imgs.clear();
				return 1;
			} else {
				imgs.clear();
				return -1;
			}
		} else {
			imgs.clear();
			return -2;
		}
	}

	
	//리뷰 업데이트
	@PutMapping("/reviewUpdate/{bno}")
	public void reviewUpdate(@PathVariable Long bno, Boolean bool) {

		ReviewDTO reviewUpdate = reviewRepo.findById(bno).get();		
		reviewUpdate.setReviewExposure(bool);		
		System.out.println(reviewUpdate);
		reviewRepo.save(reviewUpdate);
	}

	
	//리뷰 삭제(로그인한 본인 것만)
	@DeleteMapping("/reviewDelete/{bno}")
//	public String reviewDelete(@PathVariable Long bno)	{
	public String reviewDelete(@PathVariable Long bno, HttpServletRequest request) {

		System.out.println("삭제요청옴");

		if (request.getSession().getAttribute("member") != null) {
			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
			ReviewDTO reviewDelete = reviewRepo.findById(bno).get();

			if (mem.getMemSeq() == reviewDelete.getMember().getMemSeq()) {

				System.out.println("정보일치");
				reviewRepo.deleteById(bno);
				return "success";
			} else {
				System.out.println("불일치");
				return "fail";
			}
		}
		return "fail";
	}


	//이미지 업로드 (개별로 한개씩 처리 후 리스트로 묶어서 입력할때 리뷰에 넣는다
	@PostMapping("/upload")
	public String reviewImg(@RequestParam MultipartFile file) throws Exception {
		System.out.println("요청들어옴");		
		
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString()+"_"+file.getOriginalFilename();
		
		String basePath = "C:/MSA/3Project/zumuniyo-react/public/img";		
		String filePath = basePath + "/" + filename;		
		File dest = new File(filePath);
		file.transferTo(dest); // 파일 업로드 작업 수행
		
		//저장되는 경로를 맞춤 리액트의 public에 폴더 img		
		String filePath2 = "img/"+ filename;		
		imgs.add(filePath2);

		return filename;
	}
	
	
	
//	//이미지 업로드 (개별로 한개씩 처리 후 리스트로 묶어서 입력할때 리뷰에 넣는다
//	@PostMapping("/upload")
//	public void reviewImg(@RequestParam MultipartFile file) throws Exception {
//		System.out.println("요청들어옴");
////		String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
//		
//		System.out.println(file);	
//		
//		String basePath = "C:/MSA/3Project/zumuniyo-react/public/img";
//		String filePath = basePath + "/" + file.getOriginalFilename();
//		
//		File dest = new File(filePath);
//		
//		file.transferTo(dest); // 파일 업로드 작업 수행
//		
//		//저장되는 경로를 맞춤 리액트의 public에 폴더 img
//		String filePath2 = "img/"+ file.getOriginalFilename();		
//		imgs.add(filePath2);
//	}


	
	
	
	
	
	
	
	
	
	
	
	//마이페이지, 관리자등 리뷰 이외 test용
	
	@PostMapping("/nickchange/{newNick}")
	public int nickchange(@PathVariable String newNick, HttpServletRequest request) {
		
		System.out.println("newNick : " + newNick);
		if (request.getSession().getAttribute("member") != null) {
			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
			mem.setMemNick(newNick);
			memRepo.save(mem);		
			System.out.println("mem :" +mem);
			return 1;
		}	
		return -1;		
	}
	
	@PostMapping("/memManage")
	public String memManage(MemberDTO newMem, HttpServletRequest request) {
		
		if(newMem==null) {
			return "입력 MemberDTO값이 없음";
		}
		
		if (request.getSession().getAttribute("member") != null) {
			MemberDTO mem = (MemberDTO) request.getSession().getAttribute("member");
			mem.setMemNick(newMem.getMemNick());
			mem.setMemStatus(newMem.getMemStatus());
			mem.setMemType(newMem.getMemType());
			memRepo.save(mem);			
			return "성공";
		}	
		return "세션의 로그인 정보가 없음";		
	}
	
	@PostMapping("/memList")
	public List<MemberDTO> memAllList(){
		List<MemberDTO> memList =  (List<MemberDTO>) memRepo.findAll(Sort.by(Sort.Direction.ASC, "memSeq"));
		System.out.println("memList: "+memList);		
		return memList;
	}
		
	@PostMapping("/orderList")
	public List<OrderGroupDTO> orderList(){
		List<OrderGroupDTO> orderList =  (List<OrderGroupDTO>) orderGRepo.findAll(Sort.by(Sort.Direction.DESC, "orderGroupSeq"));
		System.out.println("orderList: "+orderList);
		
		return orderList;
	}
	
	
	@PostMapping("/shopList")
	public List<ShopDTO> shopAllList(){
		List<ShopDTO> shopList =  (List<ShopDTO>) shopRepo.findAll(Sort.by(Sort.Direction.ASC, "shopSeq"));
		System.out.println("memList: "+shopList);		
		return shopList;
	}
	
	
	

	
	
	
	
	
}
