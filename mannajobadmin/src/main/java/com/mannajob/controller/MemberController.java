package com.mannajob.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mannajob.domain.MemberVO;
import com.mannajob.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/")
@AllArgsConstructor
public class MemberController {
	private MemberService service;
	
	/*
	 * @GetMapping("/login") public void login() {
	 * 
	 * }
	 */
	
	@PostMapping ("/login")
	public String login(MemberVO member, HttpServletRequest request, HttpSession session) {
		//�α��� �����ϸ� ������������, �����ϸ� �α��� ������
		if(service.LoginSelect(member)) {
			log.info("�α��� ������");
			session.setAttribute("m_id", member.getM_id());
			return "redirect:/main"; 
		} else {
			log.info("�α��� ����");
			return "redirect:/login";
		}
	}
	
	//�α׾ƿ� �� �α��� ��������
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/login";
	}
}