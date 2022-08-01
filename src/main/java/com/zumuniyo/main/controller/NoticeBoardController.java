package com.zumuniyo.main.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.querydsl.core.types.Predicate;
import com.zumuniyo.main.dto.NoticeBoardDTO;
import com.zumuniyo.main.dto.PageMaker;
import com.zumuniyo.main.dto.PageVO;
import com.zumuniyo.main.repository.NoticeBoardRepository;
import com.zumuniyo.main.repository.NoticeBoardService;

import lombok.extern.java.Log;
@Log
@RestController
@RequestMapping("/NoticeBoard")
public class NoticeBoardController {

	@Autowired
	NoticeBoardRepository boardRepo;
	
	@Autowired
	NoticeBoardService service;
	
	@PostMapping("/NoticeUpdate.do")
	public String modifyPost(PageVO pageVO, @RequestBody NoticeBoardDTO board) {
		System.out.println("보드입니다:"+board);
		boardRepo.findById(board.getNoticeBoardSeq()).ifPresentOrElse(original->{
			original.setContent(board.getContent());
			original.setTitle(board.getTitle());
			NoticeBoardDTO updateBoard = boardRepo.save(original);
		}, ()->{System.out.println("수정할 데이터없음");});
		
		return "수정완료";
		
	}
	
	@Transactional
	@DeleteMapping("/NoticeDelete.do/{boardSeq}")
	public String delete(@PathVariable Long boardSeq )
								{
		
		boardRepo.findById(boardSeq).ifPresent(re->{
			boardRepo.delete(re);
		});
		return "수정완료";
	}


	
	
	@GetMapping("/Noticedetail.do/{boardSeq}")
	public NoticeBoardDTO boardDetail(@PathVariable Long boardSeq){
			
		service.updateView(boardSeq);
		
		return boardRepo.findById(boardSeq).get(); 
	}
	
	@PostMapping("/NoticeInsert.go")
	public String registerPost(@RequestBody NoticeBoardDTO board, RedirectAttributes attr) {
		NoticeBoardDTO insertBoard =  boardRepo.save(board);
		//attr.addFlashAttribute("message", insertBoard!=null?"insert success":"insert fail");
		//return "redirect:/board/boardlist.go";
		return "insert OK";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/Noticelist.go")
	public List<NoticeBoardDTO> boardlist( @ModelAttribute  PageVO pageVO,    Model model, 
			HttpSession session,
			HttpServletRequest request) {
		//System.out.println(session.getServletContext().getContextPath());
		
	
		
		Map<String, Object> map = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		if(map!=null) {
			String message = (String)map.get("message");
			model.addAttribute("msg", message);
			pageVO = (PageVO) map.get("pageVO"); 
		}
		if(pageVO == null) pageVO = new PageVO();
		
		System.out.println("pageVO:" + pageVO);
		
		Pageable page = pageVO.makePaging(0, new String[]{"boardTop", "noticeBoardSeq"}); //direction
		Predicate predicate = 
				boardRepo.makePredicate(pageVO.getType(), pageVO.getKeyword());
		
		
		Page<NoticeBoardDTO> blist =  boardRepo.findAll(predicate, page);
		
		
		
		
		PageMaker<NoticeBoardDTO> pgmaker = new PageMaker<>(blist);
		
		
		System.out.println(blist.getNumber());
		System.out.println(blist.getSize());
		System.out.println(blist.getTotalPages());
		System.out.println(blist.getContent());
		
			
		model.addAttribute("blist", pgmaker);
		//model.addAttribute("pageVO", pageVO);
		return blist.getContent();
	}
}
