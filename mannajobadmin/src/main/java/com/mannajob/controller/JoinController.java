package com.mannajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//ȸ������ �������� insert.jsp
	@GetMapping("/insert")
	public void insert() {
		
	}
	
	//MemberVO�� ���� ���� INSERT �� login.jsp�� redirect
	@PostMapping("/insert")
	public String insert(MemberVO member, RedirectAttributes rttr) {
		log.info("����..............................");
		
		service.Join(member);
		rttr.addFlashAttribute("result", 1);
		
		return "redirect:/login";
	}

}
