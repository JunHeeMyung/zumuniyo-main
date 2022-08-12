package com.zumuniyo.main.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.querydsl.core.types.Predicate;
import com.zumuniyo.main.dto.AdvertisementDTO;
import com.zumuniyo.main.dto.PageMaker;
import com.zumuniyo.main.dto.PageVO;
import com.zumuniyo.main.repository.AdvertisementRepository;

import lombok.extern.java.Log;


@Log
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {
	
	@Autowired
	AdvertisementRepository advertisementRepository; 

	
	@Transactional
	@DeleteMapping("/deleteAd/{adSeq}")
	public String delete(@PathVariable Long adSeq )
								{
		
		advertisementRepository.findById(adSeq).ifPresent(re->{
			advertisementRepository.delete(re);
		});
		return "수정완료";
	}

	
	
	@GetMapping("/advertisementDetail/{adSeq}")
	public AdvertisementDTO adDetail(@PathVariable Long adSeq){
		System.out.println("/advertisementDetail....."+adSeq);
		
		return advertisementRepository.findById(adSeq).get(); 
	}
	
	
	
	@PostMapping("/advertisementUpdate")
	public String modifyPost(@RequestBody AdvertisementDTO advertisement) {
		System.out.println("보드입니다:"+advertisement);
		advertisementRepository.findById(advertisement.getAdSeq()).ifPresentOrElse(original->{
			original.setOwner(advertisement.getOwner());
			original.setEndTime(advertisement.getEndTime());
			original.setStartTime(advertisement.getStartTime());
			original.setImage(advertisement.getImage());
			AdvertisementDTO updateAdvertisement= advertisementRepository.save(original);
		}, ()->{System.out.println("수정할 데이터없음");});
		
		return "수정완료";
		
	}
	
	@PostMapping("/advertisementinsert")
	public String registerPost(@RequestParam(defaultValue = "") String owner,
								@RequestParam(defaultValue = "0") String endTime,
								@RequestParam(defaultValue = "0") String startTime,
								@RequestParam String image,
								RedirectAttributes attr) {
		System.out.println("endtime:"+ endTime);
	
			AdvertisementDTO ad = AdvertisementDTO.builder()
					.endTime(Timestamp.valueOf(endTime))
					.startTime(Timestamp.valueOf(startTime))
					.owner(owner)
					.image(image)
					.build();
			System.out.println("endtime2"+endTime);
			
			advertisementRepository.save(ad);
//		
		
		//attr.addFlashAttribute("message", insertBoard!=null?"insert success":"insert fail");
		//return "redirect:/board/boardlist.go";
		return "insert OK";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/adlist")
	public List<AdvertisementDTO> adlist( @ModelAttribute  PageVO pageVO,    Model model, 
			HttpSession session,
			HttpServletRequest request) {
		//System.out.println(session.getServletContext().getContextPath());
		System.out.println("여기왔습니다.");
	
		
		Map<String, Object> map = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		if(map!=null) {
			String message = (String)map.get("message");
			model.addAttribute("msg", message);
			pageVO = (PageVO) map.get("pageVO"); 
		}
		if(pageVO == null) pageVO = new PageVO();
		
		System.out.println("pageVO:" + pageVO);
		
		Pageable page = pageVO.makePaging(0,  "adSeq"); //direction
		Predicate predicate = 
				advertisementRepository.makePredicate(pageVO.getType(), pageVO.getKeyword());
		
		
		Page<AdvertisementDTO> alist =  advertisementRepository.findAll(predicate, page);
		
		
		
		PageMaker<AdvertisementDTO> pgmaker = new PageMaker<>(alist);
		
		
		System.out.println(alist.getNumber());
		System.out.println(alist.getSize());
		System.out.println(alist.getTotalPages());
		System.out.println(alist.getContent());
		
			
		model.addAttribute("alist", pgmaker);
		//model.addAttribute("pageVO", pageVO);
		return alist.getContent();
	}
}
