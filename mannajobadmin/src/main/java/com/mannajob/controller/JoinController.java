package com.mannajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mannajob.domain.EmplVO;
import com.mannajob.domain.MemberVO;
import com.mannajob.service.JoinService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/join/*")
@AllArgsConstructor
public class JoinController {
	private JoinService service;
	
	//ȸ������ �������� member.jsp
	@GetMapping("/member")
	public void insert() {
		
	}
	
	//MemberVO�� ���� ���� INSERT �� login.jsp�� redirect
	@PostMapping("/member")
	public String insert(MemberVO member, RedirectAttributes rttr) {
		log.info("����..............................");
		
		service.MemJoin(member);
		rttr.addFlashAttribute("result", 1);
		
		return "redirect:/login";
	}
	
	//����� ��� �������� empl.jsp
	@GetMapping("/empl")
	public void EmplJoin() {
			
	}
		
	//EmplVO�� ���� ���� INSERT �� mypage�� redirect
	@PostMapping("/empl")
	public String EmplJoin(EmplVO empl, RedirectAttributes rttr) {
		log.info("����..............................");
			
		service.EmplJoin(empl);
		rttr.addFlashAttribute("result", 1);
		
		return "redirect:/mypage/main";
	}

}
